package org.eclipse.ui.part;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.util.Tracing;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.dialogs.IPageChangeProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IKeyBindingService;
import org.eclipse.ui.INestableKeyBindingService;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.PartSite;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.misc.Policy;
import org.eclipse.ui.internal.services.INestable;
import org.eclipse.ui.internal.services.IServiceLocatorCreator;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.services.IDisposable;
import org.eclipse.ui.services.IServiceLocator;

public abstract class MultiPageEditorPart extends EditorPart implements IPageChangeProvider {
	
	private static final String COMMAND_NEXT_SUB_TAB = "org.eclipse.ui.navigate.nextSubTab"; //$NON-NLS-1$
	private static final String COMMAND_PREVIOUS_SUB_TAB = "org.eclipse.ui.navigate.previousSubTab"; //$NON-NLS-1$
	
	protected static final int PAGE_CONTAINER_SITE = 65535;

	private static final String TRACING_COMPONENT = "MPE"; //$NON-NLS-1$

	private INestable activeServiceLocator;

	private CTabFolder container;

	private ArrayList nestedEditors = new ArrayList(3);
	
	private List pageSites = new ArrayList(3);

	private IServiceLocator pageContainerSite;
	
	private ListenerList pageChangeListeners = new ListenerList(
			ListenerList.IDENTITY);

	protected MultiPageEditorPart() {
		super();
	}

	public int addPage(Control control) {
		int index = getPageCount();
		addPage(index, control);
		return index;
	}

	public void addPage(int index, Control control) {
		createItem(index, control);
	}

	public int addPage(IEditorPart editor, IEditorInput input)
			throws PartInitException {
		int index = getPageCount();
		addPage(index, editor, input);
		return index;
	}

	public void addPage(int index, IEditorPart editor, IEditorInput input)
			throws PartInitException {
		IEditorSite site = createSite(editor);
		editor.init(site, input);
		Composite parent2 = new Composite(getContainer(),
				getOrientation(editor));
		parent2.setLayout(new FillLayout());
		editor.createPartControl(parent2);
		editor.addPropertyListener(new IPropertyListener() {
			@Override
			public void propertyChanged(Object source, int propertyId) {
				MultiPageEditorPart.this.handlePropertyChange(propertyId);
			}
		});
		Item item = createItem(index, parent2);
		item.setData(editor);
		nestedEditors.add(editor);
	}

	private int getOrientation(IEditorPart editor) {
		if (editor instanceof IWorkbenchPartOrientation) {
			return ((IWorkbenchPartOrientation) editor).getOrientation();
		}
		return getOrientation();
	}

	private CTabFolder createContainer(Composite parent) {
		parent.setLayout(new FillLayout());
		final CTabFolder newContainer = new CTabFolder(parent, SWT.BOTTOM
				| SWT.FLAT);
		newContainer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int newPageIndex = newContainer.indexOf((CTabItem) e.item);
				pageChange(newPageIndex);
			}
		});
		newContainer.addTraverseListener(new TraverseListener() { 
			@Override
			public void keyTraversed(TraverseEvent e) {
				switch (e.detail) {
					case SWT.TRAVERSE_PAGE_NEXT:
					case SWT.TRAVERSE_PAGE_PREVIOUS:
						int detail = e.detail;
						e.doit = true;
						e.detail = SWT.TRAVERSE_NONE;
						Control control = newContainer.getParent();
						do {
							if (control.traverse(detail))
								return;
							if (control.getListeners(SWT.Traverse).length != 0)
								return;
							if (control instanceof Shell)
								return;
							control = control.getParent();
						} while (control != null);
				}
			}
		});
		return newContainer;
	}

	private CTabItem createItem(int index, Control control) {
		CTabItem item = new CTabItem(getTabFolder(), SWT.NONE, index);
		item.setControl(control);
		return item;
	}

	protected abstract void createPages();

	@Override
	public final void createPartControl(Composite parent) {
		Composite pageContainer = createPageContainer(parent);
		this.container = createContainer(pageContainer);
		createPages();
		if (getActivePage() == -1) {
			setActivePage(0);
			IEditorPart part = getEditor(0);
			if (part!=null) {
				final IServiceLocator serviceLocator = part.getEditorSite();
				if (serviceLocator instanceof INestable) {
					activeServiceLocator = (INestable) serviceLocator;
					activeServiceLocator.activate();
				}
			}
		}
		initializePageSwitching();
		initializeSubTabSwitching();
	}

	protected void initializePageSwitching() {
		new PageSwitcher(this) {
			@Override
			public Object[] getPages() {
				int pageCount = getPageCount();
				Object[] result = new Object[pageCount];
				for (int i = 0; i < pageCount; i++) {
					result[i] = new Integer(i);
				}
				return result;
			}

			@Override
			public String getName(Object page) {
				return getPageText(((Integer) page).intValue());
			}

			@Override
			public ImageDescriptor getImageDescriptor(Object page) {
				Image image = getPageImage(((Integer) page).intValue());
				if (image == null)
					return null;

				return ImageDescriptor.createFromImage(image);
			}

			@Override
			public void activatePage(Object page) {
				setActivePage(((Integer) page).intValue());
			}

			@Override
			public int getCurrentPageIndex() {
				return getActivePage();
			}
		};
	}

	private void initializeSubTabSwitching() {
		IHandlerService service = getSite().getService(IHandlerService.class);
		service.activateHandler(COMMAND_NEXT_SUB_TAB, new AbstractHandler() {
			@Override
			public Object execute(ExecutionEvent event) throws ExecutionException {
				int n= getPageCount();
				if (n == 0)
					return null;
				
				int i= getActivePage() + 1;
				if (i >= n)
					i= 0;
				setActivePage(i);
				return null;
			}
		});
		
		service.activateHandler(COMMAND_PREVIOUS_SUB_TAB, new AbstractHandler() {
			@Override
			public Object execute(ExecutionEvent event) throws ExecutionException {
				int n= getPageCount();
				if (n == 0)
					return null;
				
				int i= getActivePage() - 1;
				if (i < 0)
					i= n - 1;
				setActivePage(i);
				return null;
			}
		});
	}
	
	protected Composite createPageContainer(Composite parent) {
		return parent;
	}

	protected IEditorSite createSite(IEditorPart editor) {
		return new MultiPageEditorSite(this, editor);
	}

	@Override
	public void dispose() {
		deactivateSite(true, false);

		pageChangeListeners.clear();
		for (int i = 0; i < nestedEditors.size(); ++i) {
			IEditorPart editor = (IEditorPart) nestedEditors.get(i);
			disposePart(editor);
		}
		nestedEditors.clear();
		if (pageContainerSite instanceof IDisposable) {
			((IDisposable) pageContainerSite).dispose();
			pageContainerSite = null;
		}
		for (int i = 0; i < pageSites.size(); i++) {
			IServiceLocator sl = (IServiceLocator) pageSites.get(i);
			if (sl instanceof IDisposable) {
				((IDisposable) sl).dispose();
			}
		}
		pageSites.clear();
		super.dispose();
	}

	protected IEditorPart getActiveEditor() {
		int index = getActivePage();
		if (index != -1) {
			return getEditor(index);
		}
		return null;
	}

	public int getActivePage() {
		CTabFolder tabFolder = getTabFolder();
		if (tabFolder != null && !tabFolder.isDisposed()) {
			return tabFolder.getSelectionIndex();
		}
		return -1;
	}

	protected Composite getContainer() {
		return container;
	}

	protected Control getControl(int pageIndex) {
		return getItem(pageIndex).getControl();
	}

	protected IEditorPart getEditor(int pageIndex) {
		Item item = getItem(pageIndex);
		if (item != null) {
			Object data = item.getData();
			if (data instanceof IEditorPart) {
				return (IEditorPart) data;
			}
		}
		return null;
	}
	
	protected final IServiceLocator getPageSite(int pageIndex) {
		if (pageIndex == PAGE_CONTAINER_SITE) {
			return getPageContainerSite();
		}
		
		Item item = getItem(pageIndex);
		if (item != null) {
			Object data = item.getData();
			if (data instanceof IEditorPart) {
				return ((IEditorPart) data).getSite();
			} else if (data instanceof IServiceLocator) {
				return (IServiceLocator) data;
			} else if (data == null) {
				IServiceLocatorCreator slc = getSite()
						.getService(IServiceLocatorCreator.class);
				IServiceLocator sl = slc.createServiceLocator(getSite(), null, new IDisposable(){
					@Override
					public void dispose() {
						close();
					}
				});
				item.setData(sl);
				pageSites.add(sl);
				return sl;
			}
		}
		return null;
	}

	void close() {
		PartSite partSite = (PartSite) getSite();
		MPart model = partSite.getModel();
		Widget widget = (Widget) model.getWidget();
		if (widget != null && !widget.isDisposed()) {
			getSite().getPage().closeEditor(MultiPageEditorPart.this, true);
		}
	}

	private IServiceLocator getPageContainerSite() {
		if (pageContainerSite == null) {
			IServiceLocatorCreator slc = getSite()
					.getService(IServiceLocatorCreator.class);
			pageContainerSite = slc.createServiceLocator(getSite(), null, new IDisposable(){
				@Override
				public void dispose() {
					close();
				}
			});
		}
		return pageContainerSite;
	}

	private CTabItem getItem(int pageIndex) {
		return getTabFolder().getItem(pageIndex);
	}

	protected int getPageCount() {
		CTabFolder folder = getTabFolder();
		if (folder != null && !folder.isDisposed()) {
			return folder.getItemCount();
		}
		return 0;
	}

	protected Image getPageImage(int pageIndex) {
		return getItem(pageIndex).getImage();
	}

	protected String getPageText(int pageIndex) {
		return getItem(pageIndex).getText();
	}

	private CTabFolder getTabFolder() {
		return container;
	}

	protected void handlePropertyChange(int propertyId) {
		firePropertyChange(propertyId);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		site.setSelectionProvider(new MultiPageSelectionProvider(this));
	}

	@Override
	public boolean isDirty() {
		for (Iterator i = nestedEditors.iterator(); i.hasNext();) {
			IEditorPart editor = (IEditorPart) i.next();
			if (editor.isDirty()) {
				return true;
			}
		}
		return false;
	}

	protected void pageChange(int newPageIndex) {
		deactivateSite(false, false);

		IPartService partService = getSite().getService(
				IPartService.class);
		if (partService.getActivePart() == this) {
			setFocus();
		}

		IEditorPart activeEditor = getEditor(newPageIndex);

		IEditorActionBarContributor contributor = getEditorSite()
				.getActionBarContributor();
		if (contributor != null
				&& contributor instanceof MultiPageEditorActionBarContributor) {
			((MultiPageEditorActionBarContributor) contributor)
					.setActivePage(activeEditor);
		}

		if (activeEditor != null) {
			ISelectionProvider selectionProvider = activeEditor.getSite()
					.getSelectionProvider();
			if (selectionProvider != null) {
				ISelectionProvider outerProvider = getSite()
						.getSelectionProvider();
				if (outerProvider instanceof MultiPageSelectionProvider) {
					SelectionChangedEvent event = new SelectionChangedEvent(
							selectionProvider, selectionProvider.getSelection());

					MultiPageSelectionProvider provider = (MultiPageSelectionProvider) outerProvider;
					provider.fireSelectionChanged(event);
					provider.firePostSelectionChanged(event);
				} else {
					if (Policy.DEBUG_MPE) {
						Tracing.printTrace(TRACING_COMPONENT,
								"MultiPageEditorPart " + getTitle() //$NON-NLS-1$
										+ " did not propogate selection for " //$NON-NLS-1$
										+ activeEditor.getTitle());
					}
				}
			}
		}

		activateSite();
		Object selectedPage = getSelectedPage();
		if (selectedPage != null) {
			firePageChanged(new PageChangedEvent(this, selectedPage));
		}
	}
	
	protected final void deactivateSite(boolean immediate,
			boolean containerSiteActive) {
		if (activeServiceLocator != null) {
			activeServiceLocator.deactivate();
			activeServiceLocator = null;
		}

		final int pageIndex = getActivePage();
		final IKeyBindingService service = getSite().getKeyBindingService();
		if (pageIndex < 0 || pageIndex >= getPageCount() || immediate) {
			if (service instanceof INestableKeyBindingService) {
				final INestableKeyBindingService nestableService = (INestableKeyBindingService) service;
				nestableService.activateKeyBindingService(null);
			} else {
				WorkbenchPlugin
						.log("MultiPageEditorPart.deactivateSite()   Parent key binding service was not an instance of INestableKeyBindingService.  It was an instance of " + service.getClass().getName() + " instead."); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		
		if (containerSiteActive) {
			IServiceLocator containerSite = getPageContainerSite();
			if (containerSite instanceof INestable) {
				activeServiceLocator = (INestable) containerSite;
				activeServiceLocator.activate();
			}
		}
	}
	
	protected final void activateSite() {
		if (activeServiceLocator != null) {
			activeServiceLocator.deactivate();
			activeServiceLocator = null;
		}

		final IKeyBindingService service = getSite().getKeyBindingService();
		final int pageIndex = getActivePage();
		final IEditorPart editor = getEditor(pageIndex);

		if (editor != null) {
			if (service instanceof INestableKeyBindingService) {
				final INestableKeyBindingService nestableService = (INestableKeyBindingService) service;
				nestableService.activateKeyBindingService(editor
						.getEditorSite());

			} else {
				WorkbenchPlugin
						.log("MultiPageEditorPart.activateSite()   Parent key binding service was not an instance of INestableKeyBindingService.  It was an instance of " + service.getClass().getName() + " instead."); //$NON-NLS-1$ //$NON-NLS-2$
			}
			final IServiceLocator serviceLocator = editor.getEditorSite();
			if (serviceLocator instanceof INestable) {
				activeServiceLocator = (INestable) serviceLocator;
				activeServiceLocator.activate();
			}

		} else {
			Item item = getItem(pageIndex);

			if (service instanceof INestableKeyBindingService) {
				final INestableKeyBindingService nestableService = (INestableKeyBindingService) service;
				nestableService.activateKeyBindingService(null);
			} else {
				WorkbenchPlugin
						.log("MultiPageEditorPart.activateSite()   Parent key binding service was not an instance of INestableKeyBindingService.  It was an instance of " + service.getClass().getName() + " instead."); //$NON-NLS-1$ //$NON-NLS-2$
			}

			if (item.getData() instanceof INestable) {
				activeServiceLocator = (INestable) item.getData();
				activeServiceLocator.activate();
			}
		}
	}

	private void disposePart(final IWorkbenchPart part) {
		SafeRunner.run(new ISafeRunnable() {
			@Override
			public void run() {
				IWorkbenchPartSite partSite = part.getSite();
				part.dispose();
				if (partSite instanceof MultiPageEditorSite) {
					((MultiPageEditorSite) partSite).dispose();
				}
			}

			@Override
			public void handleException(Throwable e) {
			}
		});
	}

	public void removePage(int pageIndex) {
		Assert.isTrue(pageIndex >= 0 && pageIndex < getPageCount());
		IEditorPart editor = getEditor(pageIndex);

		CTabItem item = getItem(pageIndex);
		IServiceLocator pageLocator = null;
		if (item.getData() instanceof IServiceLocator) {
			pageLocator = (IServiceLocator) item.getData();
		}
		Control pageControl = item.getControl();

		item.dispose();

		if (pageControl != null) {
			pageControl.dispose();
		}

		if (editor != null) {
			nestedEditors.remove(editor);
			disposePart(editor);
		}
		if (pageLocator != null) {
			pageSites.remove(pageLocator);
			if (pageLocator instanceof IDisposable) {
				((IDisposable) pageLocator).dispose();
			}
		}
	}

	protected void setActivePage(int pageIndex) {
		Assert.isTrue(pageIndex >= 0 && pageIndex < getPageCount());
		getTabFolder().setSelection(pageIndex);
		pageChange(pageIndex);
	}

	protected void setControl(int pageIndex, Control control) {
		getItem(pageIndex).setControl(control);
	}

	@Override
	public void setFocus() {
		setFocus(getActivePage());
	}

	private void setFocus(int pageIndex) {
		if (pageIndex < 0 || pageIndex >= getPageCount()) {
			return;
		}
		final IEditorPart editor = getEditor(pageIndex);
		if (editor != null) {
			editor.setFocus();

		} else {
			final Control control = getControl(pageIndex);
			if (control != null) {
				control.setFocus();
			}
		}
	}

	protected void setPageImage(int pageIndex, Image image) {
		getItem(pageIndex).setImage(image);
	}

	protected void setPageText(int pageIndex, String text) {
		getItem(pageIndex).setText(text);
	}

	@Override
	public Object getAdapter(Class adapter) {
		Object result = super.getAdapter(adapter);
		if (result == null && Display.getCurrent()!=null) {
			IEditorPart innerEditor = getActiveEditor();
			if (innerEditor != null && innerEditor != this) {
				result = Util.getAdapter(innerEditor, adapter);
			}
		}
		return result;
	}
	
	public final IEditorPart[] findEditors(IEditorInput input) {
		List result = new ArrayList();
		int count = getPageCount();
		for (int i = 0; i < count; i++) {
			IEditorPart editor = getEditor(i);
			if (editor != null 
					&& editor.getEditorInput() != null
					&& editor.getEditorInput().equals(input)) {
				result.add(editor);
			}
		}
		return (IEditorPart[]) result.toArray(new IEditorPart[result.size()]);
	}
	
	public final void setActiveEditor(IEditorPart editorPart) {
		int count = getPageCount();
		for (int i = 0; i < count; i++) {
			IEditorPart editor = getEditor(i);
			if (editor == editorPart) {
				setActivePage(i);
				break;
			}
		}
	}

	@Override
	public Object getSelectedPage() {
		int index = getActivePage();
		if (index == -1) {
			return null;
		}
		IEditorPart editor = getEditor(index);
		if (editor != null) {
			return editor;
		}
		return getControl(index);
	}
	
	@Override
	public void addPageChangedListener(IPageChangedListener listener) {
		pageChangeListeners.add(listener);
	}

	@Override
	public void removePageChangedListener(IPageChangedListener listener) {
		pageChangeListeners.remove(listener);
	}

	private void firePageChanged(final PageChangedEvent event) {
		Object[] listeners = pageChangeListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			final IPageChangedListener l = (IPageChangedListener) listeners[i];
			SafeRunnable.run(new SafeRunnable() {
				@Override
				public void run() {
					l.pageChanged(event);
				}
			});
		}
	}
}
