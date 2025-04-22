package org.eclipse.ui.internal.navigator.filters.search;

import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.internal.navigator.NavigatorPlugin;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;

public final class CommonNavigatorSearchFilterHelper {
	private static String FILTER_COMPOSITE = "FILTER_COMPOSITE"; //$NON-NLS-1$

	private static String BUSY_IMAGE_PATH = "/icons/full/elcl16/busy.png"; //$NON-NLS-1$

	private static CommonNavigatorSearchFilterHelper instance = new CommonNavigatorSearchFilterHelper();

	private CommonNavigatorSearchFilterHelper() {
	}

	public static CommonNavigatorSearchFilterHelper getInstance() {
		return instance;
	}

	public Composite createFilterTextField(final Composite parent, final CommonNavigator commonNavigator) {
		Composite filterComposite = new Composite(parent, SWT.NONE);
		filterComposite.setLayout(new GridLayout(2, false));
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false);
		layoutData.heightHint = 0;
		filterComposite.setLayoutData(layoutData);
		filterComposite.setVisible(false);
		filterComposite.setData(FILTER_COMPOSITE);

		Text filterText = new Text(filterComposite, SWT.BORDER | SWT.SEARCH | SWT.ICON_SEARCH | SWT.ICON_CANCEL);
		filterText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		filterText.addListener(SWT.DefaultSelection, e -> handleFilterSelectionEvent(commonNavigator, e));
		filterText.addListener(SWT.KeyDown, e -> handleKeyDownEvent(commonNavigator, e));
		filterText.addListener(SWT.Modify, e -> filterNavigatorTree(commonNavigator, filterText.getText()));

		Image busyImage = NavigatorPlugin.getDefault().getImage(BUSY_IMAGE_PATH);
		Label busyImageLabel = new Label(filterComposite, SWT.NONE);
		busyImageLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		busyImageLabel.setImage(busyImage);
		busyImageLabel.setVisible(false);

		return filterComposite;
	}

	private void handleFilterSelectionEvent(CommonNavigator commonNavigator, Event e) {
		if (e.detail == SWT.ICON_CANCEL) {
			deactivateFilter(commonNavigator.getCommonViewer());
		}
	}

	private void handleKeyDownEvent(CommonNavigator commonNavigator, Event e) {
		if (e.keyCode == SWT.ESC) {
			deactivateFilter(commonNavigator.getCommonViewer());
		}
	}

	private Composite getFilterComposite(CommonViewer viewer) {
		Composite contentComposite = viewer.getControl().getParent().getParent();
		Control child = contentComposite.getChildren()[0];
		if (child instanceof Composite && child.getData() == FILTER_COMPOSITE) {
			return (Composite) child;
		}
		return null;
	}

	public void activateFilter(CommonViewer viewer) {
		Composite filterComposite = getFilterComposite(viewer);
		if (filterComposite != null) {
			filterComposite.setVisible(true);
			GridData layoutData = (GridData) filterComposite.getLayoutData();
			layoutData.heightHint = SWT.DEFAULT;
			filterComposite.getParent().layout(true);

			Text filterTextField = (Text) filterComposite.getChildren()[0];
			filterTextField.setFocus();
		}
	}

	public void deactivateFilter(CommonViewer viewer) {
		Composite filterComposite = getFilterComposite(viewer);
		if (filterComposite != null) {
			filterComposite.setVisible(false);
			GridData layoutData = (GridData) filterComposite.getLayoutData();
			layoutData.heightHint = 0;
			filterComposite.getParent().layout(true);
		}

		removeSearchFilterIfExist(viewer);
		viewer.refresh();
	}

	private void filterNavigatorTree(CommonNavigator commonNavigator, String searchString) {
		CommonNavigatorSearchFilter searchFilter = getOrAddSearchFilter(commonNavigator.getCommonViewer());
		searchFilter.setSearchString(searchString);
	}

	private CommonNavigatorSearchFilter getOrAddSearchFilter(CommonViewer viewer) {
		ViewerFilter[] filters = viewer.getFilters();
		for (ViewerFilter filter : filters) {
			if (filter instanceof CommonNavigatorSearchFilter) {
				return (CommonNavigatorSearchFilter) filter;
			}
		}

		CommonNavigatorSearchFilter newFilter = new CommonNavigatorSearchFilter(viewer);
		ViewerFilter[] newFilters = new ViewerFilter[filters.length + 1];
		System.arraycopy(filters, 0, newFilters, 0, filters.length);
		newFilters[filters.length] = newFilter;
		viewer.setFilters(newFilters);
		return newFilter;
	}

	private void removeSearchFilterIfExist(CommonViewer commonViewer) {
		ViewerFilter[] filters = commonViewer.getFilters();
		for (int i = 0; i < filters.length; ++i) {
			if (filters[i] instanceof CommonNavigatorSearchFilter) {
				CommonNavigatorSearchFilter filterToRemove = (CommonNavigatorSearchFilter) filters[i];
				filterToRemove.dispose();
				ViewerFilter[] newFilters = new ViewerFilter[filters.length - 1];
				System.arraycopy(filters, 0, newFilters, 0, i);
				System.arraycopy(filters, i + 1, newFilters, i, filters.length - i - 1);
				commonViewer.setFilters(newFilters);
				return;
			}
		}
	}

	public void setBusyImageVisible(CommonViewer commonViewer, boolean visible) {
		Composite filterComposite = getFilterComposite(commonViewer);
		if (filterComposite != null) {
			Control label = filterComposite.getChildren()[1];
			label.setVisible(visible);
		}
	}
}
