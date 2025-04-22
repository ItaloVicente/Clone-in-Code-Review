package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionDelta;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IRegistryChangeEvent;
import org.eclipse.core.runtime.IRegistryChangeListener;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.ContributionsAnalyzer;
import org.eclipse.e4.ui.internal.workbench.OpaqueElementUtil;
import org.eclipse.e4.ui.internal.workbench.swt.AbstractPartRenderer;
import org.eclipse.e4.ui.internal.workbench.swt.MenuService;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.model.application.ui.menu.MPopupMenu;
import org.eclipse.e4.ui.model.application.ui.menu.impl.MenuFactoryImpl;
import org.eclipse.e4.ui.workbench.renderers.swt.MenuManagerRenderer;
import org.eclipse.e4.ui.workbench.swt.factories.IRendererFactory;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener2;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.SubMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public class PopupMenuExtender implements IMenuListener2,
		IRegistryChangeListener {
    
    private static final int STATIC_ACTION_READ = 1;

    private static final int INCLUDE_EDITOR_INPUT = 1 << 1;

    private final MenuManager menu;

    private SubMenuManager menuWrapper;

    private final ISelectionProvider selProvider;

    private final IWorkbenchPart part;

	private Map<String, ViewerActionBuilder> staticActionBuilders = null;

	private int bitSet = 0;
	
	private ArrayList<PluginActionContributionItem> actionContributionCache = new ArrayList<PluginActionContributionItem>();
	private boolean cleanupNeeded = false;

	private MPart modelPart;

	private IEclipseContext context;

	public PopupMenuExtender(String id, MenuManager menu, ISelectionProvider prov,
			IWorkbenchPart part, IEclipseContext context) {
		this(id, menu, prov, part, context, true);
	}

	public PopupMenuExtender(final String id, final MenuManager menu,
			final ISelectionProvider prov, final IWorkbenchPart part, IEclipseContext context,
			final boolean includeEditorInput) {
		super();
		this.menu = menu;
		this.selProvider = prov;
		this.part = part;
		this.context = context;
		this.modelPart = (MPart) part.getSite().getService(MPart.class);
		if (includeEditorInput) {
			bitSet |= INCLUDE_EDITOR_INPUT;
		}
		menu.addMenuListener(this);
		if (!menu.getRemoveAllWhenShown()) {
			menuWrapper = new SubMenuManager(menu);
			menuWrapper.setVisible(true);
		}
		createModelFor(id);
		addMenuId(id);
				
		Platform.getExtensionRegistry().addRegistryChangeListener(this);
	}

	private void createModelFor(String id) {
		if (id == null) {
			id = getClass().getName() + '.' + System.identityHashCode(this);
		}
		menuModel = null;
		for (MMenu item : modelPart.getMenus()) {
			if (id.equals(item.getElementId()) && item instanceof MPopupMenu
					&& item.getTags().contains("popup")) { //$NON-NLS-1$
				menuModel = (MPopupMenu) item;
				break;
			}
		}
		if (menuModel == null) {
			menuModel = MenuFactoryImpl.eINSTANCE.createPopupMenu();
			menuModel.setElementId(id);
			menuModel.getTags().add(ContributionsAnalyzer.MC_POPUP);
			modelPart.getMenus().add(menuModel);
		}
		IRendererFactory factory = modelPart.getContext().get(IRendererFactory.class);
		AbstractPartRenderer obj = factory.getRenderer(menuModel, null);
		if (obj instanceof MenuManagerRenderer) {
			((MenuManagerRenderer) obj).linkModelToManager(menuModel, menu);
		}
		registerE4Support();
		cleanUpContributionCache();
	}

	private void registerE4Support() {
		if (menuModel.getWidget() == null && menu.getMenu() != null) {
			MenuService.registerMenu(menu.getMenu().getParent(), menuModel, context);
		}
	}
	public Set<String> getMenuIds() {
    	if (staticActionBuilders == null) {
			return Collections.emptySet();
    	}
    	
        return staticActionBuilders.keySet();
    }

    public final void addMenuId(final String menuId) {
		bitSet &= ~STATIC_ACTION_READ;
		if (menuModel != null) {
			List<String> tags = menuModel.getTags();
			String tag = "popup:" + menuId; //$NON-NLS-1$
			if (!tags.contains(tag)) {
				tags.add(tag);
			}
		}
		readStaticActionsFor(menuId);
	}

    public final boolean matches(final MenuManager menuManager,
            final ISelectionProvider selectionProvider,
            final IWorkbenchPart part) {
        return (this.menu == menuManager)
                && (this.selProvider == selectionProvider)
                && (this.part == part);
    }

	private void addEditorActions(IMenuManager mgr, Set<IObjectActionContributor> alreadyContributed) {
        ISelectionProvider activeEditor = new ISelectionProvider() {

            @Override
			public void addSelectionChangedListener(
                    ISelectionChangedListener listener) {
                throw new UnsupportedOperationException(
                "This ISelectionProvider is static, and cannot be modified."); //$NON-NLS-1$
            }

            @Override
			public ISelection getSelection() {
                if (part instanceof IEditorPart) {
                    final IEditorPart editorPart = (IEditorPart) part;
                    return new StructuredSelection(new Object[] { editorPart
                            .getEditorInput() });
                }

                return new StructuredSelection(new Object[0]);
            }

            @Override
			public void removeSelectionChangedListener(
                    ISelectionChangedListener listener) {
                throw new UnsupportedOperationException(
                "This ISelectionProvider is static, and cannot be modified."); //$NON-NLS-1$
            }

            @Override
			public void setSelection(ISelection selection) {
                throw new UnsupportedOperationException(
                        "This ISelectionProvider is static, and cannot be modified."); //$NON-NLS-1$
            }
        };
        
		if (ObjectActionContributorManager.getManager().contributeObjectActions(part, mgr,
				activeEditor, alreadyContributed)) {
			mgr.add(new Separator());
		}
    }

	private void addObjectActions(IMenuManager mgr, Set<IObjectActionContributor> alreadyContributed) {
        if (selProvider != null) {
			if (ObjectActionContributorManager.getManager().contributeObjectActions(part, mgr,
					selProvider, alreadyContributed)) {
				mgr.add(new Separator());
            }
        }
    }
    
    private final void clearStaticActions() {
		bitSet &= ~STATIC_ACTION_READ;
		if (staticActionBuilders != null) {
			final Iterator<ViewerActionBuilder> staticActionBuilderItr = staticActionBuilders
					.values().iterator();
			while (staticActionBuilderItr.hasNext()) {
				final ViewerActionBuilder staticActionBuilder = staticActionBuilderItr.next();
				staticActionBuilder.dispose();
			}
		}
	}

    private void addStaticActions(IMenuManager mgr) {
		if (staticActionBuilders != null) {
			final Iterator<ViewerActionBuilder> staticActionBuilderItr = staticActionBuilders
					.values().iterator();
			while (staticActionBuilderItr.hasNext()) {
				final ViewerActionBuilder staticActionBuilder = staticActionBuilderItr.next();
				staticActionBuilder.contribute(mgr, null, true);
			}
		}
	}

    @Override
	public void menuAboutToShow(IMenuManager mgr) {
		registerE4Support();
    	
    	final IWorkbenchPartSite site = part.getSite();
    	if (site != null) {
			final IWorkbench workbench = site.getWorkbenchWindow()
					.getWorkbench();
			if (workbench instanceof Workbench) {
				final Workbench realWorkbench = (Workbench) workbench;
				runCleanUp(realWorkbench);
				ISelection input = null;
				if ((bitSet & INCLUDE_EDITOR_INPUT) != 0) {
					if (part instanceof IEditorPart) {
						final IEditorPart editorPart = (IEditorPart) part;
						input = new StructuredSelection(
								new Object[] { editorPart.getEditorInput() });
					}
				}
				ISelection s = (selProvider == null ? null : selProvider
						.getSelection());
				realWorkbench.addShowingMenus(getMenuIds(), s, input);
			}
		}
    	
		addMenuContributions(mgr);

    	readStaticActions();
        if (menuWrapper != null) {
            mgr = menuWrapper;
            menuWrapper.removeAll();
        }
		Set<IObjectActionContributor> contributedItems = new HashSet<IObjectActionContributor>();
        if ((bitSet & INCLUDE_EDITOR_INPUT) != 0) {
			addEditorActions(mgr, contributedItems);
        }
		addObjectActions(mgr, contributedItems);
        addStaticActions(mgr);
    }
    

	private void addMenuContributions(IMenuManager mgr) {
		IRendererFactory factory = modelPart.getContext().get(IRendererFactory.class);
		AbstractPartRenderer obj = factory.getRenderer(menuModel, null);
		if (obj instanceof MenuManagerRenderer) {
			MenuManagerRenderer renderer = (MenuManagerRenderer) obj;
			renderer.reconcileManagerToModel(menu, menuModel);
			renderer.processContributions(menuModel, menuModel.getElementId(), false, true);
			renderer.processContents((MElementContainer<MUIElement>) ((Object) menuModel));
		}
	}

	private MPopupMenu menuModel;
    
    @Override
	public final void menuAboutToHide(final IMenuManager mgr) {
    	gatherContributions(mgr);
		cleanupNeeded = true;
    	final IWorkbenchPartSite site = part.getSite();
    	if (site != null) {
    		final IWorkbench workbench = site.getWorkbenchWindow().getWorkbench();
    		if (workbench instanceof Workbench) {
				workbench.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						final Workbench realWorkbench = (Workbench) workbench;
						runCleanUp(realWorkbench);
					}
				});
			}
    	}
    }

	private void runCleanUp(Workbench realWorkbench) {
		if (!cleanupNeeded) {
			return;
		}
		cleanupNeeded = false;
		realWorkbench.removeShowingMenus(getMenuIds(), null, null);
		cleanUpContributionCache();
	}

	private void gatherContributions(final IMenuManager mgr) {
		final IContributionItem[] items = mgr.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i] instanceof PluginActionContributionItem) {
				actionContributionCache.add((PluginActionContributionItem) items[i]);
			} else if (items[i] instanceof IMenuManager) {
				gatherContributions(((IMenuManager) items[i]));
			}
		}
	}
	
	private void cleanUpContributionCache() {
		if (!actionContributionCache.isEmpty()) {
			PluginActionContributionItem[] items = actionContributionCache
					.toArray(new PluginActionContributionItem[actionContributionCache.size()]);
			actionContributionCache.clear();
			for (int i = 0; i < items.length; i++) {
				items[i].dispose();
			}
		}

		if (modelPart == null || menuModel == null) {
			return;
		}
		IEclipseContext modelContext = modelPart.getContext();
		if (modelContext != null) {
			IRendererFactory factory = modelContext.get(IRendererFactory.class);
			if (factory != null) {
				AbstractPartRenderer obj = factory.getRenderer(menuModel, null);
				if (obj instanceof MenuManagerRenderer) {
					MenuManagerRenderer renderer = (MenuManagerRenderer) obj;
					renderer.cleanUp(menuModel);
				}
			}
		}
	}

    private final void readStaticActions() {
    	if (staticActionBuilders != null) {
			final Iterator<String> menuIdItr = staticActionBuilders.keySet().iterator();
			while (menuIdItr.hasNext()) {
				final String menuId = menuIdItr.next();
				readStaticActionsFor(menuId);
			}
		}
    }

    private void readStaticActionsFor(final String menuId) {
		if ((bitSet & STATIC_ACTION_READ) != 0) {
			return;
		}

		bitSet |= STATIC_ACTION_READ;

		if ((menuId == null) || (menuId.length() < 1)) {
			return;
		}

		if (staticActionBuilders == null) {
			staticActionBuilders = new HashMap<String, ViewerActionBuilder>();
		}

		Object object = staticActionBuilders.get(menuId);
		if (!(object instanceof ViewerActionBuilder)) {
			object = new ViewerActionBuilder();
			staticActionBuilders.put(menuId, (ViewerActionBuilder) object);
		}
		final ViewerActionBuilder staticActionBuilder = (ViewerActionBuilder) object;
		staticActionBuilder.readViewerContributions(menuId, selProvider, part);
	}

    public void dispose() {
		clearStaticActions();
		Platform.getExtensionRegistry().removeRegistryChangeListener(this);
		menu.removeMenuListener(this);

		if (menuModel != null) {
			IRendererFactory factory = modelPart.getContext().get(IRendererFactory.class);
			AbstractPartRenderer obj = factory.getRenderer(menuModel, null);
			if (obj instanceof MenuManagerRenderer) {
				MenuManagerRenderer renderer = (MenuManagerRenderer) obj;
				unlink(renderer, menuModel);
				renderer.clearModelToManager(menuModel, menu);
			}

			modelPart.getMenus().remove(menuModel);
		}
	}

	private void unlink(MenuManagerRenderer renderer, MMenu menu) {
		for (MMenuElement menuElement : menu.getChildren()) {
			if (OpaqueElementUtil.isOpaqueMenuItem(menuElement)
					|| OpaqueElementUtil.isOpaqueMenuSeparator(menuElement)) {
				Object item = OpaqueElementUtil.getOpaqueItem(menuElement);
				if (item instanceof IContributionItem) {
					renderer.clearModelToContribution(menuElement, (IContributionItem) item);
					OpaqueElementUtil.clearOpaqueItem(menuElement);
				}
			} else if (menuElement instanceof MMenu) {
				MMenu subMenu = (MMenu) menuElement;
				unlink(renderer, subMenu);
				MenuManager manager = renderer.getManager(subMenu);
				if (manager != null) {
					renderer.clearModelToManager(subMenu, manager);
				}
			} else {
				IContributionItem contribution = renderer.getContribution(menuElement);
				if (contribution != null) {
					renderer.clearModelToContribution(menuElement, contribution);
				}
			}
		}
	}

	@Override
	public void registryChanged(final IRegistryChangeEvent event) {
		Display display = Display.getDefault();
		if (part != null) {
			display = part.getSite().getPage().getWorkbenchWindow().getWorkbench().getDisplay();
		}
		IExtensionDelta [] deltas = event.getExtensionDeltas();
		for (int i = 0; i < deltas.length; i++) {
			IExtensionDelta delta = deltas[i];
			IExtensionPoint extensionPoint = delta.getExtensionPoint();
			if (extensionPoint.getContributor().getName().equals(WorkbenchPlugin.PI_WORKBENCH)
					&& extensionPoint.getSimpleIdentifier().equals(
							IWorkbenchRegistryConstants.PL_POPUP_MENU)) {

				boolean clearPopups = false;
				IConfigurationElement [] elements = delta.getExtension().getConfigurationElements();
				for (int j = 0; j < elements.length; j++) {
					IConfigurationElement element = elements[j];
					if (element.getName().equals(IWorkbenchRegistryConstants.TAG_VIEWER_CONTRIBUTION)) {
						clearPopups = true;
						break;
					}					
				}
										
				if (clearPopups) {
					display.syncExec(new Runnable() {
						@Override
						public void run() {
							clearStaticActions();
						}
					});
				}
			}
		}
	}
	
	public MenuManager getManager() {
		return menu;
	}
}
