
package org.eclipse.ui.part;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.SubActionBars;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.util.Util;

public abstract class PageBookView extends ViewPart implements IPartListener {
	private PageBook book;

	private PageRec defaultPageRec;

	private Map mapPartToRec = new HashMap();

	private Map mapPageToSite = new HashMap();

	private Map mapPageToNumRecs = new HashMap();

	private PageRec activeRec;

	private IWorkbenchPart hiddenPart = null;

	private IPropertyChangeListener actionBarPropListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getProperty().equals(SubActionBars.P_ACTION_HANDLERS)
					&& activeRec != null
					&& event.getSource() == activeRec.subActionBars) {
				refreshGlobalActionHandlers();
			}
		}
	};

	private ISelectionChangedListener selectionChangedListener = new ISelectionChangedListener() {
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			pageSelectionChanged(event);
		}
	};

	private ISelectionChangedListener postSelectionListener = new ISelectionChangedListener() {
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			postSelectionChanged(event);
		}
	};

	private SelectionProvider selectionProvider = new SelectionProvider();

	protected static class PageRec {

		public IWorkbenchPart part;

		public IPage page;

		public SubActionBars subActionBars;

		public PageRec(IWorkbenchPart part, IPage page) {
			this.part = part;
			this.page = page;
		}

		public void dispose() {
			part = null;
			page = null;
		}
	}

	private static class SelectionManager extends EventManager {
		public void addSelectionChangedListener(
				ISelectionChangedListener listener) {
			addListenerObject(listener);
		}

		public void removeSelectionChangedListener(
				ISelectionChangedListener listener) {
			removeListenerObject(listener);
		}

		public void selectionChanged(final SelectionChangedEvent event) {
			Object[] listeners = getListeners();
			for (int i = 0; i < listeners.length; ++i) {
				final ISelectionChangedListener l = (ISelectionChangedListener) listeners[i];
				SafeRunner.run(new SafeRunnable() {
					@Override
					public void run() {
						l.selectionChanged(event);
					}
				});
			}
		}

	}

	protected class SelectionProvider implements IPostSelectionProvider {

		private SelectionManager fSelectionListener = new SelectionManager();

		private SelectionManager fPostSelectionListeners = new SelectionManager();

		@Override
		public void addSelectionChangedListener(
				ISelectionChangedListener listener) {
			fSelectionListener.addSelectionChangedListener(listener);
		}

		@Override
		public ISelection getSelection() {
			IPage currentPage = getCurrentPage();
			if (currentPage == null) {
				return StructuredSelection.EMPTY;
			}
			IPageSite site = getPageSite(currentPage);
			if (site == null) {
				return StructuredSelection.EMPTY;
			}
			ISelectionProvider selProvider = site.getSelectionProvider();
			if (selProvider != null) {
				return selProvider.getSelection();
			}
			return StructuredSelection.EMPTY;
		}

		@Override
		public void removeSelectionChangedListener(
				ISelectionChangedListener listener) {
			fSelectionListener.removeSelectionChangedListener(listener);
		}

		public void selectionChanged(final SelectionChangedEvent event) {
			fSelectionListener.selectionChanged(event);
		}

		public void postSelectionChanged(final SelectionChangedEvent event) {
			fPostSelectionListeners.selectionChanged(event);			
		}

		@Override
		public void setSelection(ISelection selection) {
			IPage currentPage = getCurrentPage();
			if (currentPage == null) {
				return;
			}
			IPageSite site = getPageSite(currentPage);
			if (site == null) {
				return;
			}
			ISelectionProvider selProvider = site.getSelectionProvider();
			if (selProvider != null) {
				selProvider.setSelection(selection);
			}
		}

		@Override
		public void addPostSelectionChangedListener(
				ISelectionChangedListener listener) {
			fPostSelectionListeners.addSelectionChangedListener(listener);
		}

		@Override
		public void removePostSelectionChangedListener(
				ISelectionChangedListener listener) {
			fPostSelectionListeners.removeSelectionChangedListener(listener);
		}
	}

	protected PageBookView() {
		super();
	}

	protected abstract IPage createDefaultPage(PageBook book);

	private PageRec createPage(IWorkbenchPart part) {
		PageRec rec = doCreatePage(part);
		if (rec != null) {
			mapPartToRec.put(part, rec);
			preparePage(rec);
		}
		return rec;
	}

	private void preparePage(PageRec rec) {
		IPageSite site = null;
		Integer count;

		if (!doesPageExist(rec.page)) {
			if (rec.page instanceof IPageBookViewPage) {
				site = ((IPageBookViewPage) rec.page).getSite();
			}
			if (site == null) {
				site = new PageSite(getViewSite());
			}
			mapPageToSite.put(rec.page, site);

			rec.subActionBars = (SubActionBars) site.getActionBars();
			rec.subActionBars.addPropertyChangeListener(actionBarPropListener);
			rec.page.setActionBars(rec.subActionBars);

			count = new Integer(0);
		} else {
			site = (IPageSite) mapPageToSite.get(rec.page);
			rec.subActionBars = (SubActionBars) site.getActionBars();
			count = ((Integer) mapPageToNumRecs.get(rec.page));
		}

		mapPageToNumRecs.put(rec.page, new Integer(count.intValue() + 1));
	}

	protected void initPage(IPageBookViewPage page) {
		try {
			page.init(new PageSite(getViewSite()));
		} catch (PartInitException e) {
			WorkbenchPlugin.log(getClass(), "initPage", e); //$NON-NLS-1$
		}
	}

	@Override
	public void createPartControl(Composite parent) {

		book = new PageBook(parent, SWT.NONE);

		IPage defaultPage = createDefaultPage(book);
		defaultPageRec = new PageRec(null, defaultPage);
		preparePage(defaultPageRec);

		showPageRec(defaultPageRec);

		getSite().getPage().addPartListener(partListener);
		showBootstrapPart();
	}

	@Override
	public void dispose() {
		getSite().getPage().removePartListener(partListener);

		activeRec = null;
		if (defaultPageRec != null) {
			removePage(defaultPageRec, false);
			defaultPageRec = null;
		}
		Map clone = (Map) ((HashMap) mapPartToRec).clone();
		Iterator itr = clone.values().iterator();
		while (itr.hasNext()) {
			PageRec rec = (PageRec) itr.next();
			removePage(rec, true);
		}

		super.dispose();
	}

	protected abstract PageRec doCreatePage(IWorkbenchPart part);

	protected abstract void doDestroyPage(IWorkbenchPart part,
			PageRec pageRecord);

	protected boolean doesPageExist(IPage page) {
		return mapPageToNumRecs.containsKey(page);
	}

	@Override
	public Object getAdapter(Class key) {
		IPage page = getCurrentPage();
		Object adapter = Util.getAdapter(page, key);
		if (adapter != null) {
			return adapter;
		}
		adapter = getViewAdapter(key);
		if (adapter != null) {
			return adapter;
		}
		return super.getAdapter(key);
	}

	protected Object getViewAdapter(Class adapter) {
		return null;
	}

	protected abstract IWorkbenchPart getBootstrapPart();

	protected IWorkbenchPart getCurrentContributingPart() {
		if (activeRec == null) {
			return null;
		}
		return activeRec.part;
	}

	public IPage getCurrentPage() {
		if (activeRec == null) {
			return null;
		}
		return activeRec.page;
	}

	protected PageSite getPageSite(IPage page) {
		return (PageSite) mapPageToSite.get(page);
	}

	public IPage getDefaultPage() {
		return defaultPageRec.page;
	}

	protected PageBook getPageBook() {
		return book;
	}

	protected PageRec getPageRec(IWorkbenchPart part) {
		return (PageRec) mapPartToRec.get(part);
	}

	protected PageRec getPageRec(IPage page) {
		Iterator itr = mapPartToRec.values().iterator();
		while (itr.hasNext()) {
			PageRec rec = (PageRec) itr.next();
			if (rec.page == page) {
				return rec;
			}
		}
		return null;
	}

	protected abstract boolean isImportant(IWorkbenchPart part);

	@Override
	public void init(IViewSite site) throws PartInitException {
		site.setSelectionProvider(selectionProvider);
		super.init(site);
	}

	@Override
	public void partActivated(IWorkbenchPart part) {
		if (isImportant(part)) {
			hiddenPart = null;

			PageRec rec = getPageRec(part);
			if (rec == null) {
				rec = createPage(part);
			}

			if (rec != null) {
				showPageRec(rec);
			} else {
				showPageRec(defaultPageRec);
			}
		}

		if (part == this) {
			PageSite pageSite = getPageSite(getCurrentPage());
			if (pageSite != null) {
				IEclipseContext pageContext = pageSite.getSiteContext();
				if (pageContext != null) {
					pageContext.activate();
				}
			}
		}
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		if (activeRec != null && activeRec.part == part) {
			showPageRec(defaultPageRec);
		}

		PageRec rec = getPageRec(part);
		if (rec != null) {
			removePage(rec, true);
		}
		if (part == hiddenPart) {
			hiddenPart = null;
		}
	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
	}

	@Override
	public void partOpened(IWorkbenchPart part) {
	}

	private void refreshGlobalActionHandlers() {
		IActionBars bars = getViewSite().getActionBars();
		bars.clearGlobalActionHandlers();

		Map newActionHandlers = activeRec.subActionBars
				.getGlobalActionHandlers();
		if (newActionHandlers != null) {
			Set keys = newActionHandlers.entrySet();
			Iterator iter = keys.iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				bars.setGlobalActionHandler((String) entry.getKey(),
						(IAction) entry.getValue());
			}
		}
	}

	private void removePage(PageRec rec, boolean doDestroy) {
		mapPartToRec.remove(rec.part);

		int newCount = ((Integer) mapPageToNumRecs.get(rec.page)).intValue() - 1;

		if (newCount == 0) {
			Object site = mapPageToSite.remove(rec.page);
			mapPageToNumRecs.remove(rec.page);

			Control control = rec.page.getControl();
			if (control != null && !control.isDisposed()) {
				control.dispose();
			}

			if (doDestroy) {
				doDestroyPage(rec.part, rec);
			}

			if (rec.subActionBars != null) {
				rec.subActionBars.dispose();
			}

			if (site instanceof PageSite) {
				((PageSite) site).dispose();
			}
		} else {
			mapPageToNumRecs.put(rec.page, new Integer(newCount));
		}
	}

	@Override
	public void setFocus() {
		if (book != null) {
			book.setFocus();
		}
		if (activeRec != null) {
			activeRec.page.setFocus();
		}
	}

	private void pageSelectionChanged(SelectionChangedEvent event) {
		SelectionProvider provider = (SelectionProvider) getSite()
				.getSelectionProvider();
		if (provider != null) {
			provider.selectionChanged(event);
		}
	}

	private void postSelectionChanged(SelectionChangedEvent event) {
		SelectionProvider provider = (SelectionProvider) getSite()
				.getSelectionProvider();
		if (provider != null) {
			provider.postSelectionChanged(event);
		}
	}

	private void showBootstrapPart() {
		IWorkbenchPart part = getBootstrapPart();
		if (part != null) {
			partActivated(part);
		}
	}

	protected void showPageRec(PageRec pageRec) {
		if (activeRec == pageRec) {
			return;
		}
		if (activeRec != null && pageRec != null
				&& activeRec.page == pageRec.page) {
			activeRec = pageRec;
			return;
		}

		if (activeRec != null) {
			PageSite pageSite = (PageSite) mapPageToSite.get(activeRec.page);

			activeRec.subActionBars.deactivate();

			pageSite.deactivate();

			ISelectionProvider provider = pageSite.getSelectionProvider();
			if (provider != null) {
				provider
						.removeSelectionChangedListener(selectionChangedListener);
				if (provider instanceof IPostSelectionProvider) {
					((IPostSelectionProvider) provider)
							.removePostSelectionChangedListener(postSelectionListener);
				} else {
					provider.removeSelectionChangedListener(postSelectionListener);
				}
			}
		}

		activeRec = pageRec;
		Control pageControl = activeRec.page.getControl();
		if (pageControl != null && !pageControl.isDisposed()) {
			PageSite pageSite = (PageSite) mapPageToSite.get(activeRec.page);

			book.showPage(pageControl);
			activeRec.subActionBars.activate();
			refreshGlobalActionHandlers();

			pageSite.activate();

			ISelectionProvider provider = pageSite.getSelectionProvider();
			if (provider == null) {
				WorkbenchPage page = (WorkbenchPage) getSite().getPage();
				MPart part = page.findPart(this);
				if (part != null) {
					part.getContext().get(ESelectionService.class)
							.setSelection(StructuredSelection.EMPTY);
				}
			} else {
				provider.addSelectionChangedListener(selectionChangedListener);
				if (provider instanceof IPostSelectionProvider) {
					((IPostSelectionProvider) provider)
							.addPostSelectionChangedListener(postSelectionListener);
				} else {
					provider.addSelectionChangedListener(postSelectionListener);
				}

				WorkbenchPage page = (WorkbenchPage) getSite().getPage();
				MPart part = page.findPart(this);
				if (part != null) {
					part.getContext().get(ESelectionService.class)
							.setSelection(provider.getSelection());
				}
			}
			getViewSite().getActionBars().updateActionBars();
		}
	}

	protected SelectionProvider getSelectionProvider() {
		return selectionProvider;
	}
	
	private IPartListener2 partListener = new IPartListener2() {
		@Override
		public void partActivated(IWorkbenchPartReference partRef) {
			if (partRef == null) {
				WorkbenchPlugin.log("partRef is null in PageBookView partActivated"); //$NON-NLS-1$
				return;
			}
			IWorkbenchPart part = partRef.getPart(false);
			PageBookView.this.partActivated(part);
		}

		@Override
		public void partBroughtToTop(IWorkbenchPartReference partRef) {
			PageBookView.this.partBroughtToTop(partRef.getPart(false));
		}

		@Override
		public void partClosed(IWorkbenchPartReference partRef) {
			PageBookView.this.partClosed(partRef.getPart(false));
		}

		@Override
		public void partDeactivated(IWorkbenchPartReference partRef) {
			PageBookView.this.partDeactivated(partRef.getPart(false));
		}

		@Override
		public void partHidden(IWorkbenchPartReference partRef) {
			PageBookView.this.partHidden(partRef.getPart(false));
		}

		@Override
		public void partInputChanged(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partOpened(IWorkbenchPartReference partRef) {
			PageBookView.this.partOpened(partRef.getPart(false));
		}

		@Override
		public void partVisible(IWorkbenchPartReference partRef) {
			PageBookView.this.partVisible(partRef.getPart(false));
		}
	};

	protected void partHidden(IWorkbenchPart part) {
		if (part == null || part != getCurrentContributingPart()) {
			return;
		}
		if (getSite().getPage().getPartState(
				getSite().getPage().getReference(part)) == IWorkbenchPage.STATE_MINIMIZED) {
			return;
		}
		if (part instanceof IViewPart) {
			final IViewPart[] viewStack = getSite().getPage()
					.getViewStack(this);
			if (containsPart(viewStack, part)) {
				return;
			}
		}
		hiddenPart = part;
		showPageRec(defaultPageRec);
	}

	private boolean containsPart(IViewPart[] viewStack, IWorkbenchPart part) {
		if (viewStack == null) {
			return false;
		}
		for (int i = 0; i < viewStack.length; i++) {
			if (viewStack[i] == part) {
				return true;
			}
		}
		return false;
	}

	protected void partVisible(IWorkbenchPart part) {
		if (part == null || part != hiddenPart) {
			return;
		}
		partActivated(part);
	}
}
