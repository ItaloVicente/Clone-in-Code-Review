package org.eclipse.ui.internal.views.properties.tabbed.view;

import org.eclipse.core.runtime.Assert;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;

import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.IContributedContentsView;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.AbstractOverridableTabListPropertySection;
import org.eclipse.ui.views.properties.tabbed.IOverridableTabListContentProvider;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptor;
import org.eclipse.ui.views.properties.tabbed.ITabItem;
import org.eclipse.ui.views.properties.tabbed.ITabSelectionListener;
import org.eclipse.ui.views.properties.tabbed.TabContents;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

class OverridableTabListContentProvider extends TabListContentProvider
		implements IOverridableTabListContentProvider, ITabSelectionListener {

	public OverridableTabListContentProvider(TabbedPropertyRegistry registry) {
		super(registry);
	}

	private TabbedPropertySheetPage tabbedPropertySheetPage;

	private TabbedPropertyViewer tabbedPropertyViewer;

	public Object[] getElements(Object inputElement) {
		if (tabbedPropertySheetPage.getCurrentTab() == null) {
			return registry.getTabDescriptors(currentPart,
					(ISelection) inputElement);
		}
		return getOverrideTabs(inputElement);
	}

	public void dispose() {
		stopListening();
		this.tabbedPropertyViewer = null;
		this.currentPart = null;
		this.tabbedPropertySheetPage = null;
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (tabbedPropertyViewer == null) {
			Assert.isTrue(viewer instanceof TabbedPropertyViewer);
			init((TabbedPropertyViewer) viewer);
		}
	}

	private void init(TabbedPropertyViewer newTabbedPropertyViewer) {
		this.tabbedPropertyViewer = newTabbedPropertyViewer;
		currentPart = tabbedPropertyViewer.getWorkbenchPart();
		if (currentPart.getAdapter(IPropertySheetPage.class) != null) {
			tabbedPropertySheetPage = (TabbedPropertySheetPage) currentPart
					.getAdapter(IPropertySheetPage.class);
		} else {
			IContributedContentsView view = (IContributedContentsView) currentPart
					.getAdapter(IContributedContentsView.class);
			if (view != null) {
				IWorkbenchPart part = view.getContributingPart();
				if (part != null) {
					tabbedPropertySheetPage = (TabbedPropertySheetPage) part
							.getAdapter(IPropertySheetPage.class);
				}
			}
		}
		Assert
				.isNotNull(
						tabbedPropertySheetPage,
						"DynamicTabListContentProvider could not find the TabbedPropertySheetPage for the active part");//$NON-NLS-1$
		startListening();
	}

	private void startListening() {
		tabbedPropertySheetPage.addTabSelectionListener(this);
	}

	private void stopListening() {
		tabbedPropertySheetPage.removeTabSelectionListener(this);
	}

	public void tabSelected(ITabDescriptor tabDescriptor) {
		AbstractOverridableTabListPropertySection section = getOverridableTabListPropertySection();
		Assert.isNotNull(section);
		int selectedIndex = tabbedPropertyViewer.getSelectionIndex();
		section.selectTab(selectedIndex);
	}

	private void setSelectedTab() {
		TabDescriptor currentSelectedTabInList = null;
		IStructuredSelection selectionFromList = (IStructuredSelection) tabbedPropertyViewer
				.getSelection();
		if (!selectionFromList.equals(StructuredSelection.EMPTY)) {
			currentSelectedTabInList = (TabDescriptor) selectionFromList
					.getFirstElement();
		}
		AbstractOverridableTabListPropertySection section = getOverridableTabListPropertySection();
		if (section == null) {
			if (currentSelectedTabInList == null) {
				TabDescriptor newSelectedTab = (TabDescriptor) tabbedPropertyViewer
						.getElementAt(0);
				if (newSelectedTab != null) {
					tabbedPropertyViewer.setSelection(new StructuredSelection(
							newSelectedTab), true);
				}
			}
			return;
		}
		ITabItem[] dynamicTabItems = section.getTabs();
		if (dynamicTabItems == null) {
			return;
		}
		int selectedTabIndex = -1;
		for (int i = 0; i < dynamicTabItems.length; i++) {
			if (dynamicTabItems[i].isSelected()) {
				selectedTabIndex = i;
			}
		}
		if (currentSelectedTabInList == null ||
				!currentSelectedTabInList.getText().equals(
						dynamicTabItems[selectedTabIndex].getText())) {
			TabDescriptor newSelectedTab = (TabDescriptor) tabbedPropertyViewer
					.getElementAt(selectedTabIndex);
			tabbedPropertyViewer.setSelection(new StructuredSelection(
					newSelectedTab), true);
		}
	}

	private ITabDescriptor[] getOverrideTabs(Object inputElement) {
		ITabDescriptor tabDescriptors[] = registry.getTabDescriptors(
				currentPart, (ISelection) inputElement);
		if (tabDescriptors == TabbedPropertyRegistry.EMPTY_DESCRIPTOR_ARRAY) {
			return tabDescriptors;
		}
		AbstractOverridableTabListPropertySection section = getOverridableTabListPropertySection();
		Assert.isNotNull(section);
		ITabItem[] tabItems = section.getTabs();
		if (tabItems == null) {
			return tabDescriptors;
		}
		ITabDescriptor[] overrideTabDescriptors = new ITabDescriptor[tabItems.length];
		TabDescriptor target = (TabDescriptor) tabDescriptors[0];
		for (int i = 0; i < tabItems.length; i++) {
			TabDescriptor cloneTabDescriptor = (TabDescriptor) target.clone();
			cloneTabDescriptor.setLabel(tabItems[i].getText());
			cloneTabDescriptor.setImage(tabItems[i].getImage());
			cloneTabDescriptor.setIndented(tabItems[i].isIndented());
			cloneTabDescriptor.setSelected(tabItems[i].isSelected());
			overrideTabDescriptors[i] = cloneTabDescriptor;
		}
		return overrideTabDescriptors;
	}

	public void overrideTabs() {
		stopListening();
		Object input = tabbedPropertyViewer.getInput();
		tabbedPropertyViewer.setInput(input);
		setSelectedTab();
		startListening();
	}

	private AbstractOverridableTabListPropertySection getOverridableTabListPropertySection() {
		TabContents tab = tabbedPropertySheetPage.getCurrentTab();
		Assert.isNotNull(tab);
		if (tab != null) {
			ISection section = tab.getSectionAtIndex(0);
			if (section instanceof AbstractOverridableTabListPropertySection) {
				return (AbstractOverridableTabListPropertySection) section;
			}
		}
		return null;
	}
}
