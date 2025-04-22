package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IKeyBindingService;
import org.eclipse.ui.IPageService;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.SubActionBars;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.contexts.SlaveContextService;
import org.eclipse.ui.internal.expressions.ActivePartExpression;
import org.eclipse.ui.internal.handlers.LegacyHandlerService;
import org.eclipse.ui.internal.menus.SlaveMenuService;
import org.eclipse.ui.internal.progress.WorkbenchSiteProgressService;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.services.IServiceLocatorCreator;
import org.eclipse.ui.internal.services.IWorkbenchLocationService;
import org.eclipse.ui.internal.services.ServiceLocator;
import org.eclipse.ui.internal.services.WorkbenchLocationService;
import org.eclipse.ui.internal.testing.WorkbenchPartTestable;
import org.eclipse.ui.menus.IMenuService;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.ui.progress.IWorkbenchSiteProgressService;
import org.eclipse.ui.services.IDisposable;
import org.eclipse.ui.services.IServiceScopes;
import org.eclipse.ui.testing.IWorkbenchPartTestable;

public abstract class PartSite implements IWorkbenchPartSite {

	public static final void registerContextMenu(final String menuId,
			final MenuManager menuManager, final ISelectionProvider selectionProvider,
			final boolean includeEditorInput, final IWorkbenchPart part, IEclipseContext context,
			final Collection menuExtenders) {
		final Iterator extenderItr = menuExtenders.iterator();
		boolean foundMatch = false;
		while (extenderItr.hasNext()) {
			final PopupMenuExtender existingExtender = (PopupMenuExtender) extenderItr
					.next();
			if (existingExtender.matches(menuManager, selectionProvider, part)) {
				existingExtender.addMenuId(menuId);
				foundMatch = true;
				break;
			}
		}

		if (!foundMatch) {
			menuExtenders.add(new PopupMenuExtender(menuId, menuManager, selectionProvider, part,
					context, includeEditorInput));
		}
	}

	private IWorkbenchPartReference partReference;

	private IWorkbenchPart part;

	private ISelectionProvider selectionProvider;

	private SubActionBars actionBars;

	private KeyBindingService keyBindingService;

	private SlavePageService pageService;

	private SlavePartService partService;

	private SlaveSelectionService selectionService;

	private SlaveContextService contextService;

	private SlaveMenuService menuService;

	protected ArrayList menuExtenders;

	private WorkbenchSiteProgressService progressService;

	protected final ServiceLocator serviceLocator;

	protected MPart model;

	private IConfigurationElement element;

	private IEclipseContext e4Context;

	private IWorkbenchWindow workbenchWindow;

	private String extensionId;

	public PartSite(MPart model, IWorkbenchPart part, IWorkbenchPartReference ref,
			IConfigurationElement element) {
		this.model = model;
		this.part = part;
		this.partReference = ref;
		this.element = element;

		MElementContainer<?> parent = (MElementContainer<?>) ((EObject) model).eContainer();
		while (!(parent instanceof MWindow)) {
			parent = (MElementContainer<?>) ((EObject) parent).eContainer(); // parent.getParent();
		}

		setWindow((MWindow) parent);

		e4Context = model.getContext();
		IServiceLocatorCreator slc = e4Context
				.get(IServiceLocatorCreator.class);
		IWorkbenchWindow workbenchWindow = getWorkbenchWindow();
		this.serviceLocator = (ServiceLocator) slc.createServiceLocator(workbenchWindow, null,
				new IDisposable() {
					@Override
					public void dispose() {
					}
				}, e4Context);
		initializeDefaultServices();
	}

	void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}

	private void setWindow(MWindow window) {
		MWindow topWindow = getTopLevelModelWindow(window);
		MApplication application = topWindow.getContext().get(MApplication.class);
		Workbench workbench = (Workbench) application.getContext().get(IWorkbench.class);

		workbenchWindow = workbench.createWorkbenchWindow(
				workbench.getDefaultPageInput(),
				workbench.getPerspectiveRegistry().findPerspectiveWithId(
						workbench.getPerspectiveRegistry().getDefaultPerspective()), topWindow,
				false);
	}

	private void initializeDefaultServices() {
		IHandlerService handlerService = new LegacyHandlerService(e4Context,
				new ActivePartExpression(part));
		e4Context.set(IHandlerService.class, handlerService);

		serviceLocator.registerService(IWorkbenchLocationService.class,
				new WorkbenchLocationService(IServiceScopes.PARTSITE_SCOPE,
						getWorkbenchWindow().getWorkbench(),
						getWorkbenchWindow(), this, null, null, 2));
		serviceLocator.registerService(IWorkbenchPartSite.class, this);
		serviceLocator.registerService(IWorkbenchPart.class, getPart());

		e4Context.set(IWorkbenchSiteProgressService.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (progressService == null) {
					progressService = new WorkbenchSiteProgressService(PartSite.this);
				}
				return progressService;
			}
		});
		e4Context.set(IProgressService.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (progressService == null) {
					progressService = new WorkbenchSiteProgressService(PartSite.this);
				}
				return progressService;
			}
		});
		e4Context.set(IKeyBindingService.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (keyBindingService == null) {
					keyBindingService = new KeyBindingService(PartSite.this);
				}

				return keyBindingService;
			}
		});
		e4Context.set(IPageService.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (pageService == null) {
					pageService = new SlavePageService(context.getParent().get(IPageService.class));
				}

				return pageService;
			}
		});
		e4Context.set(IPartService.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (partService == null) {
					partService = new SlavePartService(context.getParent().get(IPartService.class));
				}
				return partService;
			}
		});
		e4Context.set(ISelectionService.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (selectionService == null) {
					selectionService = new SlaveSelectionService(context.getParent().get(
							ISelectionService.class));
				}
				return selectionService;
			}
		});
		e4Context.set(IContextService.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (contextService == null) {
					contextService = new SlaveContextService(context.getParent().get(
							IContextService.class), new ActivePartExpression(part));
				}
				return contextService;
			}
		});
		e4Context.set(IMenuService.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (menuService == null) {
					menuService = new SlaveMenuService(context.getParent().get(IMenuService.class),
							model);
				}
				return menuService;
			}
		});
	}

	public void dispose() {
		if (menuExtenders != null) {
			HashSet managers = new HashSet(menuExtenders.size());
			for (int i = 0; i < menuExtenders.size(); i++) {
				PopupMenuExtender ext = (PopupMenuExtender) menuExtenders.get(i);
				managers.add(ext.getManager());
				ext.dispose();
			}
			if (managers.size()>0) {
				for (Iterator iterator = managers.iterator(); iterator
						.hasNext();) {
					MenuManager mgr = (MenuManager) iterator.next();
					mgr.dispose();
				}
			}
			menuExtenders = null;
		}

		 if (keyBindingService != null) {
			keyBindingService.dispose();
			keyBindingService = null;
		 }

		if (progressService != null) {
			progressService.dispose();
			progressService = null;
		}

		if (pageService != null) {
			pageService.dispose();
		}

		if (partService != null) {
			partService.dispose();
		}

		if (selectionService != null) {
			selectionService.dispose();
		}

		if (contextService != null) {
			contextService.dispose();
		}

		if (serviceLocator != null) {
			serviceLocator.dispose();
		}
		menuService = null;
		part = null;
	}

	public IActionBars getActionBars() {
		return actionBars;
	}

	@Override
	public String getId() {
		return extensionId == null ? element == null ? model.getElementId() : element
				.getAttribute(IWorkbenchRegistryConstants.ATT_ID)
				: extensionId;
	}

	@Override
	public String getPluginId() {
		return element == null ? model.getElementId() : element.getNamespaceIdentifier();
	}

	@Override
	public String getRegisteredName() {
		return element == null ? model.getLocalizedLabel() : element
				.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
	}

	@Override
	public IWorkbenchPage getPage() {
		return getWorkbenchWindow().getActivePage();
	}


	@Override
	public IWorkbenchPart getPart() {
		return part;
	}

	public IWorkbenchPartReference getPartReference() {
		return partReference;
	}

	@Override
	public ISelectionProvider getSelectionProvider() {
		return selectionProvider;
	}

	@Override
	public Shell getShell() {

		Display currentDisplay = Display.getCurrent();
		if (currentDisplay == null) {

			return getWorkbenchWindow().getShell();
		}

		Control control = (Control) model.getWidget();
		if (control != null && !control.isDisposed()) {
			return control.getShell();
		}
		MWindow window = e4Context.get(MWindow.class);
		return window == null ? getWorkbenchWindow().getShell() : (Shell) window.getWidget();
	}

	private MWindow getTopLevelModelWindow(MWindow window) {
		EObject previousParent = (EObject) window;
		EObject parent = previousParent.eContainer();
		while (!(parent instanceof MApplication)) {
			previousParent = parent;
			parent = parent.eContainer();
		}

		return (MWindow) previousParent;
	}

	@Override
	public IWorkbenchWindow getWorkbenchWindow() {
		return workbenchWindow;
	}

	@Override
	public void registerContextMenu(String menuID, MenuManager menuMgr,
			ISelectionProvider selProvider) {
		if (menuExtenders == null) {
			menuExtenders = new ArrayList(1);
		}

		registerContextMenu(menuID, menuMgr, selProvider, true, getPart(), e4Context, menuExtenders);
	}

	@Override
	public void registerContextMenu(MenuManager menuMgr,
			ISelectionProvider selProvider) {
		registerContextMenu(getId(), menuMgr, selProvider);
	}

	public String[] getContextMenuIds() {
		if (menuExtenders == null) {
			return new String[0];
		}
		ArrayList menuIds = new ArrayList(menuExtenders.size());
		for (Iterator iter = menuExtenders.iterator(); iter.hasNext();) {
			final PopupMenuExtender extender = (PopupMenuExtender) iter.next();
			menuIds.addAll(extender.getMenuIds());
		}
		return (String[]) menuIds.toArray(new String[menuIds.size()]);
	}

	public void setActionBars(SubActionBars bars) {
		actionBars = bars;
	}


	public void setPart(IWorkbenchPart newPart) {
		part = newPart;
	}


	@Override
	public void setSelectionProvider(ISelectionProvider provider) {
		selectionProvider = provider;
	}

	@Override
	public IKeyBindingService getKeyBindingService() {
		return e4Context.get(IKeyBindingService.class);
	}

	protected String getInitialScopeId() {
		return null;
	}

	@Override
	public final Object getAdapter(Class adapter) {

		if (IWorkbenchSiteProgressService.class == adapter) {
			return getService(adapter);
		}
		
		if (IWorkbenchPartTestable.class == adapter) {
			return new WorkbenchPartTestable(this);
		}

		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	public void activateActionBars(boolean forceVisibility) {
		if (serviceLocator != null) {
			serviceLocator.activate();
		}

		if (actionBars != null) {
			actionBars.activate(forceVisibility);
		}
	}

	public void deactivateActionBars(boolean forceHide) {
		if (actionBars != null) {
			actionBars.deactivate(forceHide);
		}
		if (serviceLocator != null) {
			serviceLocator.deactivate();
		}
	}

	WorkbenchSiteProgressService getSiteProgressService() {
		return (WorkbenchSiteProgressService) e4Context.get(IWorkbenchSiteProgressService.class
				.getName());
	}

	@Override
	public final Object getService(final Class key) {
		return serviceLocator.getService(key);
	}

	@Override
	public final boolean hasService(final Class key) {
		return serviceLocator.hasService(key);
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("PartSite(id="); //$NON-NLS-1$
		buffer.append(getId());
		buffer.append(",pluginId="); //$NON-NLS-1$
		buffer.append(getPluginId());
		buffer.append(",registeredName="); //$NON-NLS-1$
		buffer.append(getRegisteredName());
		buffer.append(",hashCode="); //$NON-NLS-1$
		buffer.append(hashCode());
		buffer.append(')');
		return buffer.toString();
	}

	public MPart getModel() {
		return model;
	}

	public IEclipseContext getContext() {
		return e4Context;
	}
}
