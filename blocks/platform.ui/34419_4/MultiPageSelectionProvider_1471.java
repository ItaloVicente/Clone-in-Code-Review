package org.eclipse.ui.part;

import java.util.ArrayList;
import org.eclipse.core.runtime.Assert;
import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IKeyBindingService;
import org.eclipse.ui.INestableKeyBindingService;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.KeyBindingService;
import org.eclipse.ui.internal.PartSite;
import org.eclipse.ui.internal.PopupMenuExtender;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.contexts.NestableContextService;
import org.eclipse.ui.internal.expressions.ActivePartExpression;
import org.eclipse.ui.internal.handlers.LegacyHandlerService;
import org.eclipse.ui.internal.part.IMultiPageEditorSiteHolder;
import org.eclipse.ui.internal.services.INestable;
import org.eclipse.ui.internal.services.IServiceLocatorCreator;
import org.eclipse.ui.internal.services.IWorkbenchLocationService;
import org.eclipse.ui.internal.services.ServiceLocator;
import org.eclipse.ui.internal.services.WorkbenchLocationService;
import org.eclipse.ui.services.IDisposable;
import org.eclipse.ui.services.IServiceScopes;

public class MultiPageEditorSite implements IEditorSite, INestable {

	private IEditorPart editor;

	private ArrayList menuExtenders;

	private MultiPageEditorPart multiPageEditor;

	private ISelectionChangedListener postSelectionChangedListener = null;

	private ISelectionChangedListener selectionChangedListener = null;

	private ISelectionProvider selectionProvider = null;

	private IKeyBindingService service = null;

	private final ServiceLocator serviceLocator;

	private NestableContextService contextService;

	private IEclipseContext context;

	private boolean active = false;

	public MultiPageEditorSite(MultiPageEditorPart multiPageEditor,
			IEditorPart editor) {
		Assert.isNotNull(multiPageEditor);
		Assert.isNotNull(editor);
		this.multiPageEditor = multiPageEditor;
		this.editor = editor;

		PartSite site = (PartSite) multiPageEditor.getSite();

		IServiceLocatorCreator slc = (IServiceLocatorCreator) site
				.getService(IServiceLocatorCreator.class);
		context = site.getModel().getContext().createChild("MultiPageEditorSite"); //$NON-NLS-1$
		serviceLocator = (ServiceLocator) slc.createServiceLocator(
				multiPageEditor.getSite(), null, new IDisposable(){
					@Override
					public void dispose() {
						getMultiPageEditor().close();
					}
				}, context);

		initializeDefaultServices();
	}

	private void initializeDefaultServices() {
		serviceLocator.registerService(IWorkbenchLocationService.class,
				new WorkbenchLocationService(IServiceScopes.MPESITE_SCOPE,
						getWorkbenchWindow().getWorkbench(),
						getWorkbenchWindow(), getMultiPageEditor().getSite(),
						this, null, 3));
		serviceLocator.registerService(IMultiPageEditorSiteHolder.class,
				new IMultiPageEditorSiteHolder() {
					@Override
					public MultiPageEditorSite getSite() {
						return MultiPageEditorSite.this;
					}
				});

		context.set(IContextService.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext ctxt, String contextKey) {
				if (contextService == null) {
					contextService = new NestableContextService(ctxt.getParent().get(
							IContextService.class), new ActivePartExpression(multiPageEditor));
				}
				return contextService;
			}
		});

		IHandlerService handlerService = new LegacyHandlerService(context);
		context.set(IHandlerService.class, handlerService);
	}

	@Override
	public final void activate() {
		active = true;
		context.activate();
		serviceLocator.activate();

		if (contextService != null) {
			contextService.activate();
		}
	}

	@Override
	public final void deactivate() {
		active = false;
		if (contextService != null) {
			contextService.deactivate();
		}

		serviceLocator.deactivate();
		context.deactivate();
	}

	public void dispose() {
		if (menuExtenders != null) {
			for (int i = 0; i < menuExtenders.size(); i++) {
				((PopupMenuExtender) menuExtenders.get(i)).dispose();
			}
			menuExtenders = null;
		}

		if (service != null) {
			IKeyBindingService parentService = getMultiPageEditor().getEditorSite()
					.getKeyBindingService();
			if (parentService instanceof INestableKeyBindingService) {
				INestableKeyBindingService nestableParent = (INestableKeyBindingService) parentService;
				nestableParent.removeKeyBindingService(this);
			}
			if (service instanceof KeyBindingService) {
				((KeyBindingService) service).dispose();
			}
			service = null;
		}

		if (contextService != null) {
			contextService.dispose();
		}

		if (serviceLocator != null) {
			serviceLocator.dispose();
		}
		context.dispose();
	}

	@Override
	public IEditorActionBarContributor getActionBarContributor() {
		return null;
	}

	@Override
	public IActionBars getActionBars() {
		return multiPageEditor.getEditorSite().getActionBars();
	}

	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Deprecated
	public ILabelDecorator getDecoratorManager() {
		return getWorkbenchWindow().getWorkbench().getDecoratorManager()
				.getLabelDecorator();
	}

	public IEditorPart getEditor() {
		return editor;
	}

	@Override
	public String getId() {
		return ""; //$NON-NLS-1$
	}

	@Override
	public IKeyBindingService getKeyBindingService() {
		if (service == null) {
			service = getMultiPageEditor().getEditorSite()
					.getKeyBindingService();
			if (service instanceof INestableKeyBindingService) {
				INestableKeyBindingService nestableService = (INestableKeyBindingService) service;
				service = nestableService.getKeyBindingService(this);

			} else {
				WorkbenchPlugin
						.log("MultiPageEditorSite.getKeyBindingService()   Parent key binding service was not an instance of INestableKeyBindingService.  It was an instance of " + service.getClass().getName() + " instead."); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return service;
	}

	public MultiPageEditorPart getMultiPageEditor() {
		return multiPageEditor;
	}

	@Override
	public IWorkbenchPage getPage() {
		return getMultiPageEditor().getSite().getPage();
	}

	@Override
	public IWorkbenchPart getPart() {
		return editor;
	}

	@Override
	public String getPluginId() {
		return ""; //$NON-NLS-1$
	}

	private ISelectionChangedListener getPostSelectionChangedListener() {
		if (postSelectionChangedListener == null) {
			postSelectionChangedListener = new ISelectionChangedListener() {
				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					MultiPageEditorSite.this.handlePostSelectionChanged(event);
				}
			};
		}
		return postSelectionChangedListener;
	}

	@Override
	public String getRegisteredName() {
		return ""; //$NON-NLS-1$
	}

	private ISelectionChangedListener getSelectionChangedListener() {
		if (selectionChangedListener == null) {
			selectionChangedListener = new ISelectionChangedListener() {
				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					MultiPageEditorSite.this.handleSelectionChanged(event);
				}
			};
		}
		return selectionChangedListener;
	}

	@Override
	public ISelectionProvider getSelectionProvider() {
		return selectionProvider;
	}

	@Override
	public final Object getService(final Class key) {
		Object service = serviceLocator.getService(key);
		if (active && service instanceof INestable) {
			((INestable) service).activate();
		}
		return service;
	}

	@Override
	public Shell getShell() {
		return getMultiPageEditor().getSite().getShell();
	}

	@Override
	public IWorkbenchWindow getWorkbenchWindow() {
		return getMultiPageEditor().getSite().getWorkbenchWindow();
	}

	protected void handlePostSelectionChanged(SelectionChangedEvent event) {
		ISelectionProvider parentProvider = getMultiPageEditor().getSite()
				.getSelectionProvider();
		if (parentProvider instanceof MultiPageSelectionProvider) {
			SelectionChangedEvent newEvent = new SelectionChangedEvent(
					parentProvider, event.getSelection());
			MultiPageSelectionProvider prov = (MultiPageSelectionProvider) parentProvider;
			prov.firePostSelectionChanged(newEvent);
		}
	}

	protected void handleSelectionChanged(SelectionChangedEvent event) {
		ISelectionProvider parentProvider = getMultiPageEditor().getSite()
				.getSelectionProvider();
		if (parentProvider instanceof MultiPageSelectionProvider) {
			SelectionChangedEvent newEvent = new SelectionChangedEvent(
					parentProvider, event.getSelection());
			MultiPageSelectionProvider prov = (MultiPageSelectionProvider) parentProvider;
			prov.fireSelectionChanged(newEvent);
		}
	}

	@Override
	public final boolean hasService(final Class key) {
		return serviceLocator.hasService(key);
	}

	@Override
	public void registerContextMenu(MenuManager menuManager,
			ISelectionProvider selProvider) {
		getMultiPageEditor().getSite().registerContextMenu(menuManager,
				selProvider);
	}

	@Override
	public final void registerContextMenu(final MenuManager menuManager,
			final ISelectionProvider selectionProvider,
			final boolean includeEditorInput) {
		registerContextMenu(getId(), menuManager, selectionProvider,
				includeEditorInput);
	}

	@Override
	public void registerContextMenu(String menuID, MenuManager menuMgr,
			ISelectionProvider selProvider) {
		if (menuExtenders == null) {
			menuExtenders = new ArrayList(1);
		}
		PartSite.registerContextMenu(menuID, menuMgr, selProvider, true, editor, context,
				menuExtenders);
	}

	@Override
	public final void registerContextMenu(final String menuId,
			final MenuManager menuManager,
			final ISelectionProvider selectionProvider,
			final boolean includeEditorInput) {
		if (menuExtenders == null) {
			menuExtenders = new ArrayList(1);
		}
		PartSite.registerContextMenu(menuId, menuManager, selectionProvider, includeEditorInput,
				editor, context, menuExtenders);
	}

	@Override
	public void setSelectionProvider(ISelectionProvider provider) {
		ISelectionProvider oldSelectionProvider = selectionProvider;
		selectionProvider = provider;
		if (oldSelectionProvider != null) {
			oldSelectionProvider
					.removeSelectionChangedListener(getSelectionChangedListener());
			if (oldSelectionProvider instanceof IPostSelectionProvider) {
				((IPostSelectionProvider) oldSelectionProvider)
						.removePostSelectionChangedListener(getPostSelectionChangedListener());
			} else {
				oldSelectionProvider
						.removeSelectionChangedListener(getPostSelectionChangedListener());
			}
		}
		if (selectionProvider != null) {
			selectionProvider
					.addSelectionChangedListener(getSelectionChangedListener());
			if (selectionProvider instanceof IPostSelectionProvider) {
				((IPostSelectionProvider) selectionProvider)
						.addPostSelectionChangedListener(getPostSelectionChangedListener());
			} else {
				selectionProvider.addSelectionChangedListener(getPostSelectionChangedListener());
			}
		}
	}
}
