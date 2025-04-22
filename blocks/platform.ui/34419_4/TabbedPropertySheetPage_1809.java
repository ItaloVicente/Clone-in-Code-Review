package org.eclipse.ui.views.properties.tabbed;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyComposite;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyRegistry;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyRegistryFactory;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyTitle;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyViewer;
import org.eclipse.ui.part.IContributedContentsView;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheet;

public class TabbedPropertySheetPage
	extends Page
	implements IPropertySheetPage, ILabelProviderListener {

	private TabbedPropertyComposite tabbedPropertyComposite;

	private TabbedPropertySheetWidgetFactory widgetFactory;

	private ITabbedPropertySheetPageContributor contributor;

	private TabbedPropertyRegistry registry;

	private ITabbedPropertySheetPageContributor selectionContributor = null;

	private String currentContributorId;

	protected IStructuredContentProvider tabListContentProvider;

	private ISelection currentSelection;

	private boolean activePropertySheet;

	private TabbedPropertyViewer tabbedPropertyViewer;

	private TabContents currentTab;

	private Map descriptorToTab;

	private Map tabToComposite;

	private List selectionQueue;

	private boolean selectionQueueLocked;

	private List tabSelectionListeners;

	private IWorkbenchWindow cachedWorkbenchWindow;

	private boolean hasTitleBar;

	private IPartListener partActivationListener = new IPartListener() {

		public void partActivated(IWorkbenchPart part) {
			handlePartActivated(part);
		}

		public void partBroughtToTop(IWorkbenchPart part) {
		}

		public void partClosed(IWorkbenchPart part) {
		}

		public void partDeactivated(IWorkbenchPart part) {
		}

		public void partOpened(IWorkbenchPart part) {
		}
	};

	private class TabbedPropertySheetPageContributorFromSelection
		implements ITabbedPropertySheetPageContributor {

		private String contributorId;

		public TabbedPropertySheetPageContributorFromSelection(
				String contributorId) {
			super();
			this.contributorId = contributorId;
		}

		public String getContributorId() {
			return contributorId;
		}

	}

	class TabbedPropertySheetPageLabelProvider
		extends LabelProvider {

		public String getText(Object element) {
			if (element instanceof ITabDescriptor) {
				return ((ITabDescriptor) element).getLabel();
			}
			return null;
		}
	}

	class SelectionChangedListener
		implements ISelectionChangedListener {

		public void selectionChanged(SelectionChangedEvent event) {
			IStructuredSelection selection = (IStructuredSelection) event
				.getSelection();
			TabContents tab = null;
			ITabDescriptor descriptor = (ITabDescriptor) selection
					.getFirstElement();

			if (descriptor == null) {
				hideTab(currentTab);
			} else {
				tab = (TabContents) descriptorToTab.get(descriptor);

				if (tab != currentTab) {
					hideTab(currentTab);
				}

				Composite tabComposite = (Composite) tabToComposite.get(tab);
				if (tabComposite == null) {
					tabComposite = createTabComposite();
					tab.createControls(tabComposite,
						TabbedPropertySheetPage.this);
					tabToComposite.put(tab, tabComposite);
				}
				tab.setInput(tabbedPropertyViewer.getWorkbenchPart(),
					(ISelection) tabbedPropertyViewer.getInput());

				storeCurrentTabSelection(descriptor.getLabel());

				if (tab != currentTab) {
					showTab(tab);
				}

				tab.refresh();
			}
			tabbedPropertyComposite.getTabComposite().layout(true);
			currentTab = tab;
			resizeScrolledComposite();

			if (descriptor != null) {
				handleTabSelection(descriptor);
			}
		}

		private void showTab(TabContents target) {
			if (target != null) {
				Composite tabComposite = (Composite) tabToComposite.get(target);
				if (tabComposite != null) {
					tabComposite.moveAbove(null);
					target.aboutToBeShown();
					tabComposite.setVisible(true);
				}
			}
		}

		private void hideTab(TabContents target) {
			if (target != null) {
				Composite tabComposite = (Composite) tabToComposite.get(target);
				if (tabComposite != null) {
					target.aboutToBeHidden();
					tabComposite.setVisible(false);
				}
			}
		}

	}

	public TabbedPropertySheetPage(
			ITabbedPropertySheetPageContributor tabbedPropertySheetPageContributor) {
		this(tabbedPropertySheetPageContributor, true);
	}
	
	public TabbedPropertySheetPage(
			ITabbedPropertySheetPageContributor tabbedPropertySheetPageContributor,
			boolean showTitleBar) {
		hasTitleBar = showTitleBar;
		contributor = tabbedPropertySheetPageContributor;
		tabToComposite = new HashMap();
		selectionQueue = new ArrayList(10);
		tabSelectionListeners = new ArrayList();
		initContributor(contributor.getContributorId());
	}

	protected void handlePartActivated(IWorkbenchPart part) {
		boolean thisActivated = part instanceof PropertySheet
			&& ((PropertySheet) part).getCurrentPage() == this;

        if (!thisActivated && !part.equals(contributor)
                && !part.getSite().getId().equals(contributor.getContributorId())) {
			IContributedContentsView view = (IContributedContentsView) part
				.getAdapter(IContributedContentsView.class);
			if (view == null
				|| (view.getContributingPart() != null && !view
					.getContributingPart().equals(contributor))) {
				if (activePropertySheet) {
					if (currentTab != null) {
						currentTab.aboutToBeHidden();
					}
					activePropertySheet = false;
				}
				return;
			}
		}
		if (!activePropertySheet && currentTab != null) {
			currentTab.aboutToBeShown();
			currentTab.refresh();
		}
		activePropertySheet = true;
	}

	public void createControl(Composite parent) {
		widgetFactory = new TabbedPropertySheetWidgetFactory();
		tabbedPropertyComposite = new TabbedPropertyComposite(parent,
			widgetFactory, hasTitleBar);
		widgetFactory.paintBordersFor(tabbedPropertyComposite);
		tabbedPropertyComposite.setLayout(new FormLayout());
		FormData formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		formData.top = new FormAttachment(0, 0);
		formData.bottom = new FormAttachment(100, 0);
		tabbedPropertyComposite.setLayoutData(formData);

		tabbedPropertyViewer = new TabbedPropertyViewer(tabbedPropertyComposite
			.getList());
		tabbedPropertyViewer.setContentProvider(tabListContentProvider);
		tabbedPropertyViewer
			.setLabelProvider(new TabbedPropertySheetPageLabelProvider());
		tabbedPropertyViewer
			.addSelectionChangedListener(new SelectionChangedListener());

		tabbedPropertyComposite.getScrolledComposite().addControlListener(
				new ControlAdapter() {

					public void controlResized(ControlEvent e) {
						resizeScrolledComposite();
					}
				});

		cachedWorkbenchWindow = getSite().getWorkbenchWindow();
		cachedWorkbenchWindow.getPartService().addPartListener(
				partActivationListener);

		if (hasTitleBar) {
			registry.getLabelProvider().addListener(this);
		}
	}

	private void initContributor(String contributorId) {
		descriptorToTab = new HashMap();
		if (contributor.getContributorId().equals(contributorId)) {
			registry = TabbedPropertyRegistryFactory.getInstance()
				.createRegistry(contributor);
		} else {
			selectionContributor = new TabbedPropertySheetPageContributorFromSelection(
				contributorId);
			registry = TabbedPropertyRegistryFactory.getInstance()
				.createRegistry(selectionContributor);
		}
		currentContributorId = contributorId;
		tabListContentProvider = getTabListContentProvider();
		hasTitleBar = hasTitleBar && registry.getLabelProvider() != null;

		if (tabbedPropertyViewer != null) {
			tabbedPropertyViewer.setContentProvider(tabListContentProvider);
		}

		if (hasTitleBar) {
			registry.getLabelProvider().addListener(this);
		}

	}

	protected IStructuredContentProvider getTabListContentProvider() {
		return registry.getTabListContentProvider();
	}

	protected void disposeContributor() {
		if (currentTab != null) {
			currentTab.aboutToBeHidden();
			currentTab = null;
		}

		disposeTabs(descriptorToTab.values());
		descriptorToTab = new HashMap();

		if (hasTitleBar && registry != null) {
			registry.getLabelProvider().removeListener(this);
		}

		if (selectionContributor != null) {
			TabbedPropertyRegistryFactory.getInstance().disposeRegistry(
				selectionContributor);
			selectionContributor = null;
		}
	}

	public void dispose() {

		disposeContributor();

		if (widgetFactory != null) {
			widgetFactory.dispose();
			widgetFactory = null;
		}
		if (cachedWorkbenchWindow != null) {
			cachedWorkbenchWindow.getPartService().removePartListener(
				partActivationListener);
			cachedWorkbenchWindow = null;
		}

		if (registry != null) {
			TabbedPropertyRegistryFactory.getInstance().disposeRegistry(
				contributor);
			registry = null;
		}
        
        contributor = null;
        currentSelection = null;
	}

	public Control getControl() {
		return tabbedPropertyComposite;
	}

	public void setActionBars(IActionBars actionBars) {
        IActionBars partActionBars = null;
		if (contributor instanceof IEditorPart) {
			IEditorPart editorPart = (IEditorPart) contributor;
            partActionBars = editorPart.getEditorSite().getActionBars();
		} else if (contributor instanceof IViewPart) {
            IViewPart viewPart = (IViewPart) contributor;
            partActionBars = viewPart.getViewSite().getActionBars();
        } 
        
        if (partActionBars != null) {
            IAction action = partActionBars.getGlobalActionHandler(ActionFactory.UNDO
                .getId());
            if (action != null) {
                actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), action);
            }
            action = partActionBars.getGlobalActionHandler(ActionFactory.REDO
                .getId()); 
            if (action != null) {
                actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), action);
            }
        }
	}

	public void setFocus() {
		getControl().setFocus();
	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		setInput(part, selection);
	}

	private void storeCurrentTabSelection(String label) {
		if (!selectionQueueLocked) {
			selectionQueue.remove(label);
			selectionQueue.add(0, label);
		}
	}

	public void resizeScrolledComposite() {
		Point currentTabSize = new Point(0, 0);
		if (currentTab != null) {
			Composite sizeReference = (Composite) tabToComposite
				.get(currentTab);
			if (sizeReference != null) {
				currentTabSize = sizeReference.computeSize(SWT.DEFAULT, SWT.DEFAULT); 
			}
		}
		tabbedPropertyComposite.getScrolledComposite().setMinSize(
				currentTabSize);

		ScrollBar verticalScrollBar = tabbedPropertyComposite
				.getScrolledComposite().getVerticalBar();
		if (verticalScrollBar != null) {
			Rectangle clientArea = tabbedPropertyComposite
					.getScrolledComposite().getClientArea();
			int increment = clientArea.height - 5;
			verticalScrollBar.setPageIncrement(increment);
		}

		ScrollBar horizontalScrollBar = tabbedPropertyComposite
				.getScrolledComposite().getHorizontalBar();
		if (horizontalScrollBar != null) {
			Rectangle clientArea = tabbedPropertyComposite
					.getScrolledComposite().getClientArea();
			int increment = clientArea.width - 5;
			horizontalScrollBar.setPageIncrement(increment);
		}
	}

	protected void disposeTabs(Collection tabs) {
		for (Iterator iter = tabs.iterator(); iter.hasNext();) {
			TabContents tab = (TabContents) iter.next();
			Composite composite = (Composite) tabToComposite.remove(tab);
			tab.dispose();
			if (composite != null) {
				composite.dispose();
			}
		}
	}

	private int getLastTabSelection(IWorkbenchPart part, ISelection input) {
		ITabDescriptor[] descriptors = registry.getTabDescriptors(part, input);
		if (descriptors.length != 0) {
			for (Iterator iter = selectionQueue.iterator(); iter.hasNext();) {
				String text = (String) iter.next();
				for (int i = 0; i < descriptors.length; i++) {
					if (text.equals(descriptors[i].getLabel())) {
						return i;
					}
				}
			}
		}
		return 0;
	}

	protected void updateTabs(ITabDescriptor[] descriptors) {
		Map newTabs = new HashMap(descriptors.length * 2);
		boolean disposingCurrentTab = (currentTab != null);
		for (int i = 0; i < descriptors.length; i++) {
			TabContents tab = (TabContents) descriptorToTab
					.remove(descriptors[i]);

			if (tab != null && tab.controlsHaveBeenCreated()) {
				if (tab == currentTab) {
					disposingCurrentTab = false;
				}
			} else {
				tab = createTab(descriptors[i]);
			}

			newTabs.put(descriptors[i], tab);
		}
		if (disposingCurrentTab) {
			currentTab.aboutToBeHidden();
			currentTab = null;
		}
		disposeTabs(descriptorToTab.values());
		descriptorToTab = newTabs;
	}

	protected TabContents createTab(ITabDescriptor tabDescriptor) {
		return tabDescriptor.createTab();
	}

	private Composite createTabComposite() {
		Composite result = widgetFactory.createComposite(
			tabbedPropertyComposite.getTabComposite(), SWT.NO_FOCUS);
		result.setVisible(false);
		result.setLayout(new FillLayout());
		FormData data = new FormData();
		if (hasTitleBar) {
			data.top = new FormAttachment(tabbedPropertyComposite.getTitle(), 0);
		} else {
			data.top = new FormAttachment(0, 0);
		}
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		result.setLayoutData(data);
		return result;
	}

	private void setInput(IWorkbenchPart part, ISelection selection) {
		if (selection.equals(currentSelection)) {
			return;
		}

		this.currentSelection = selection;

		validateRegistry(selection);
		ITabDescriptor[] descriptors = registry.getTabDescriptors(part,
				currentSelection);
		if (descriptors.length > 0) {
			updateTabs(descriptors);
		}
		tabbedPropertyViewer.setInput(part, currentSelection);
		int lastTabSelectionIndex = getLastTabSelection(part, currentSelection);
		Object selectedTab = tabbedPropertyViewer
			.getElementAt(lastTabSelectionIndex);
		selectionQueueLocked = true;
		try {
			if (selectedTab == null) {
				tabbedPropertyViewer.setSelection(null);
			} else {
				tabbedPropertyViewer.setSelection(new StructuredSelection(
					selectedTab));
			}
		} finally {
			selectionQueueLocked = false;
		}
		refreshTitleBar();
	}

	public void refresh() {
		currentTab.refresh();
	}

	public TabContents getCurrentTab() {
		return currentTab;
	}

	private void handleTabSelection(ITabDescriptor tabDescriptor) {
		if (selectionQueueLocked) {
			return;
		}
		for (Iterator i = tabSelectionListeners.iterator(); i.hasNext();) {
			ITabSelectionListener listener = (ITabSelectionListener) i.next();
			listener.tabSelected(tabDescriptor);
		}
	}

	public void addTabSelectionListener(ITabSelectionListener listener) {
		tabSelectionListeners.add(listener);
	}

	public void removeTabSelectionListener(ITabSelectionListener listener) {
		tabSelectionListeners.remove(listener);
	}

	public void overrideTabs() {
		if (tabListContentProvider instanceof IOverridableTabListContentProvider) {
			IOverridableTabListContentProvider overridableTabListContentProvider = (IOverridableTabListContentProvider) tabListContentProvider;
			overridableTabListContentProvider.overrideTabs();
		}
	}

	public TabbedPropertySheetWidgetFactory getWidgetFactory() {
		return widgetFactory;
	}

	private void refreshTitleBar() {
		if (hasTitleBar) {
			TabbedPropertyTitle title = tabbedPropertyComposite.getTitle();
			if (currentTab == null) {
				title.setTitle(null, null);
			} else {
				String text = registry.getLabelProvider().getText(
					currentSelection);
				Image image = registry.getLabelProvider().getImage(
					currentSelection);
				title.setTitle(text, image);
			}
		}
	}

	public void labelProviderChanged(LabelProviderChangedEvent event) {
		refreshTitleBar();
	}

    private ITabbedPropertySheetPageContributor getTabbedPropertySheetPageContributor(
            Object object) {
        if (object instanceof ITabbedPropertySheetPageContributor) {
            return (ITabbedPropertySheetPageContributor) object;
        }

        if (object instanceof IAdaptable
            && ((IAdaptable) object)
                .getAdapter(ITabbedPropertySheetPageContributor.class) != null) {
            return (ITabbedPropertySheetPageContributor) (((IAdaptable) object)
                .getAdapter(ITabbedPropertySheetPageContributor.class));
        }

        if (Platform.getAdapterManager().hasAdapter(object,
            ITabbedPropertySheetPageContributor.class.getName())) {
            return (ITabbedPropertySheetPageContributor) Platform
                .getAdapterManager().loadAdapter(object,
                    ITabbedPropertySheetPageContributor.class.getName());
        }

        return null;
	}

	private void validateRegistry(ISelection selection) {
		if (selection == null) {
			return;
		}

		if (!(selection instanceof IStructuredSelection)) {
			return;
		}

		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		if (structuredSelection.size() == 0) {
			return;
		}

        ITabbedPropertySheetPageContributor newContributor = getTabbedPropertySheetPageContributor(structuredSelection.getFirstElement());
        
		if (newContributor == null) {
			newContributor = contributor;
		}
		
        String selectionContributorId = newContributor.getContributorId();
		if (selectionContributorId.equals(currentContributorId)) {
			return;
		}

		Iterator i = structuredSelection.iterator();
        i.next();
		while (i.hasNext()) {
            newContributor = getTabbedPropertySheetPageContributor(i.next());
			if (newContributor == null || !newContributor.getContributorId().equals(selectionContributorId)) {
				if (selectionContributor != null) {
					disposeContributor();
					currentContributorId = contributor.getContributorId();
					initContributor(currentContributorId);
				}
				return;
			}
		}

		disposeContributor();
		currentContributorId = selectionContributorId;
		initContributor(currentContributorId);
        overrideActionBars();
	}

    private void overrideActionBars() {
        if (registry.getActionProvider() != null ) {
            IActionProvider actionProvider = registry.getActionProvider();
            actionProvider.setActionBars(contributor, getSite().getActionBars());
        }
    }

	public ITabDescriptor getSelectedTab() {
		int selectedTab = tabbedPropertyViewer.getSelectionIndex();
		if (selectedTab != -1) {
			Object object = tabbedPropertyViewer.getElementAt(selectedTab);
			if (object instanceof ITabDescriptor) {
				return (ITabDescriptor) object;
			}
		}
		return null;
	}

	public ITabDescriptor[] getActiveTabs() {
		List elements = tabbedPropertyViewer.getElements();
		if (elements != null && elements.size() > 0) {
			ITabDescriptor[] tabDescriptors = (ITabDescriptor[]) elements
					.toArray(new ITabDescriptor[0]);
			return tabDescriptors;
		}
		return new ITabDescriptor[] {};
	}
	
	public void setSelectedTab(String id) {
		List elements = tabbedPropertyViewer.getElements();
		if (elements != null && elements.size() > 0) {
			for (Iterator i = elements.iterator(); i.hasNext();) {
				ITabDescriptor tabDescriptor = (ITabDescriptor) i.next();
				if (tabDescriptor.getId() != null &&
						tabDescriptor.getId().equals(id)) {
					tabbedPropertyViewer.setSelection(new StructuredSelection(
							tabDescriptor), true);
				}
			}
		}
	}
	
    public String getTitleText(ISelection selection) {
    	if (selection == null) {
    		selection = currentSelection;
    	}
    	return registry.getLabelProvider().getText(selection);
    }
    
    public Image getTitleImage(ISelection selection) {
    	if (selection == null) {
    		selection = currentSelection;
    	}
		return registry.getLabelProvider().getImage(selection);
    }

	protected TabContents getTabContents(ITabDescriptor tabDescriptor) {
		TabContents tabContents = null;
		if (this.descriptorToTab.containsKey(tabDescriptor)) {
			tabContents = (TabContents) this.descriptorToTab.get(tabDescriptor);
		}
		return tabContents;
	}

	protected ITabbedPropertySheetPageContributor getSelectionContributor() {
		return this.selectionContributor;
	}

	protected String getCurrentContributorId() {
		return this.currentContributorId;
	}

	protected ISelection getCurrentSelection() {
		return this.currentSelection;
	}
}
