package org.eclipse.ui.part;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.SubActionBars;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.PartSite;
import org.eclipse.ui.internal.PopupMenuExtender;
import org.eclipse.ui.internal.contexts.NestableContextService;
import org.eclipse.ui.internal.expressions.ActivePartExpression;
import org.eclipse.ui.internal.handlers.LegacyHandlerService;
import org.eclipse.ui.internal.part.IPageSiteHolder;
import org.eclipse.ui.internal.services.INestable;
import org.eclipse.ui.internal.services.IServiceLocatorCreator;
import org.eclipse.ui.internal.services.IWorkbenchLocationService;
import org.eclipse.ui.internal.services.ServiceLocator;
import org.eclipse.ui.internal.services.WorkbenchLocationService;
import org.eclipse.ui.services.IDisposable;
import org.eclipse.ui.services.IServiceScopes;

public class PageSite implements IPageSite, INestable {

	private ArrayList menuExtenders;

	private IViewSite parentSite;

	private ISelectionProvider selectionProvider;

	private final ServiceLocator serviceLocator;

	private SubActionBars subActionBars;

	private IEclipseContext e4Context;

	private NestableContextService contextService;

	private boolean active = false;

	public PageSite(final IViewSite parentViewSite) {
		Assert.isNotNull(parentViewSite);
		parentSite = parentViewSite;
		subActionBars = new SubActionBars(parentViewSite.getActionBars(), this);

		IServiceLocatorCreator slc = parentSite
				.getService(IServiceLocatorCreator.class);
		e4Context = ((PartSite) parentViewSite).getContext().createChild("PageSite"); //$NON-NLS-1$
		this.serviceLocator = (ServiceLocator) slc.createServiceLocator(parentViewSite, null,
				new IDisposable() {
					@Override
					public void dispose() {
					}
				}, e4Context);
		initializeDefaultServices();
	}

	private void initializeDefaultServices() {
		serviceLocator.registerService(IWorkbenchLocationService.class,
				new WorkbenchLocationService(IServiceScopes.PAGESITE_SCOPE,
						getWorkbenchWindow().getWorkbench(),
						getWorkbenchWindow(), parentSite, null, this, 3));
		serviceLocator.registerService(IPageSiteHolder.class,
				new IPageSiteHolder() {
					@Override
					public IPageSite getSite() {
						return PageSite.this;
					}
				});

		IHandlerService handlerService = new LegacyHandlerService(e4Context);
		e4Context.set(IHandlerService.class, handlerService);

		e4Context.set(IContextService.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (contextService == null) {
					contextService = new NestableContextService(context.getParent().get(
							IContextService.class), new ActivePartExpression(parentSite.getPart()));
				}
				return contextService;
			}
		});
	}

	protected void dispose() {
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
		subActionBars.dispose();

		if (contextService != null) {
			contextService.dispose();
		}

		serviceLocator.dispose();
		e4Context.dispose();
	}

	@Override
	public IActionBars getActionBars() {
		return subActionBars;
	}

	@Override
	public Object getAdapter(Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	@Override
	public IWorkbenchPage getPage() {
		return parentSite.getPage();
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
		return parentSite.getShell();
	}

	@Override
	public IWorkbenchWindow getWorkbenchWindow() {
		return parentSite.getWorkbenchWindow();
	}

	@Override
	public final boolean hasService(final Class key) {
		return serviceLocator.hasService(key);
	}

	@Override
	public void registerContextMenu(String menuID, MenuManager menuMgr,
			ISelectionProvider selProvider) {
		if (menuExtenders == null) {
			menuExtenders = new ArrayList(1);
		}
		PartSite.registerContextMenu(menuID, menuMgr, selProvider, false, parentSite.getPart(),
				e4Context, menuExtenders);
	}

	@Override
	public void setSelectionProvider(ISelectionProvider provider) {
		selectionProvider = provider;
	}

		return e4Context;
	}

	@Override
	public void activate() {
		active = true;

		serviceLocator.activate();

		if (contextService != null) {
			contextService.activate();
		}
	}

	@Override
	public void deactivate() {
		active = false;
		if (contextService != null) {
			contextService.deactivate();
		}

		serviceLocator.deactivate();
	}
}
