package org.eclipse.ui.views.markers.internal;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.internal.ide.Policy;

public class MarkerAdapter {

	class MarkerCategory extends MarkerNode {

		MarkerAdapter markerAdapter;

		int start;

		int end;

		private ConcreteMarker[] children;

		private String name;

		MarkerCategory(MarkerAdapter adapter, int startIndex, int endIndex,
				String categoryName) {
			markerAdapter = adapter;
			start = startIndex;
			end = endIndex;
			name = categoryName;
		}

		@Override
		public MarkerNode[] getChildren() {

			if (children == null) {

				if (building) {
					return Util.EMPTY_MARKER_ARRAY;
				}

				ConcreteMarker[] allMarkers = markerAdapter.lastMarkers
						.toArray();

				int totalSize = getDisplayedSize();
				children = new ConcreteMarker[totalSize];

				System.arraycopy(allMarkers, start, children, 0, totalSize);
				view.getTableSorter().sort(view.getViewer(), children);

				for (int i = 0; i < children.length; i++) {
					children[i].setCategory(this);
				}
			}
			return children;

		}

		int getDisplayedSize() {
			if (view.getMarkerLimit() > 0) {
				return Math.min(getTotalSize(), view.getMarkerLimit());
			}
			return getTotalSize();
		}

		@Override
		public MarkerNode getParent() {
			return null;
		}

		@Override
		public String getDescription() {

			int size = end - start + 1;

			if (size <= view.getMarkerLimit()) {

				if (size == 1)
					return NLS.bind(MarkerMessages.Category_One_Item_Label,
							new Object[] { name });

				return NLS.bind(MarkerMessages.Category_Label, new Object[] {
						name, String.valueOf(getDisplayedSize()) });
			}
			return NLS.bind(MarkerMessages.Category_Limit_Label, new Object[] {
					name, String.valueOf(getDisplayedSize()),
					String.valueOf(getTotalSize()) });
		}

		private int getTotalSize() {
			return end - start + 1;
		}

		@Override
		public boolean isConcrete() {
			return false;
		}

		@Override
		public ConcreteMarker getConcreteRepresentative() {
			return markerAdapter.lastMarkers.getMarker(start);
		}

		public String getName() {
			return name;
		}
	}

	MarkerView view;

	private MarkerList lastMarkers;

	private MarkerCategory[] categories;

	private boolean building = true;// Start with nothing until we have


	MarkerAdapter(MarkerView markerView) {
		view = markerView;
	}

	public CategoryComparator getCategorySorter() {
		return (CategoryComparator) view.getViewer().getComparator();
	}

	public void buildAllMarkers(IProgressMonitor monitor) {
		building = true;
		MarkerList newMarkers;
		try {
			int markerLimit = view.getMarkerLimit();
			monitor.beginTask(MarkerMessages.MarkerView_19,
					markerLimit == -1 ? 60 : 100);
			try {
				monitor.subTask(MarkerMessages.MarkerView_waiting_on_changes);

				if (monitor.isCanceled())
					return;

				monitor
						.subTask(MarkerMessages.MarkerView_searching_for_markers);
				SubProgressMonitor subMonitor = new SubProgressMonitor(monitor,
						10);
				MarkerFilter[] filters = view.getEnabledFilters();
				if (filters.length > 0)
					newMarkers = MarkerList.compute(filters, subMonitor, true);
				else
					newMarkers = MarkerList.compute(new MarkerFilter[] { view
							.getAllFilters()[0] }, subMonitor, true);

				if (monitor.isCanceled())
					return;

				view.refreshMarkerCounts(monitor);

			} catch (CoreException e) {
				Policy.handle(e);
				newMarkers = new MarkerList();
				return;
			}

			if (monitor.isCanceled())
				return;

			ViewerComparator sorter = view.getViewer().getComparator();

			if (markerLimit == -1 || isShowingHierarchy()) {
				sorter.sort(view.getViewer(), newMarkers.toArray());
			} else {

				monitor.subTask(MarkerMessages.MarkerView_18);
				SubProgressMonitor mon = new SubProgressMonitor(monitor, 40);

				newMarkers = SortUtil.getFirst(newMarkers, (Comparator) sorter,
						markerLimit, mon);
				if (monitor.isCanceled()) 
					return;
				
				sorter.sort(view.getViewer(), newMarkers.toArray());
			}

			if (newMarkers.getSize() == 0) {
				categories = new MarkerCategory[0];
				lastMarkers = newMarkers;
				monitor.done();
				return;
			}

			monitor.subTask(MarkerMessages.MarkerView_queueing_updates);

			if (monitor.isCanceled())
				return;

			if (isShowingHierarchy()) {
				MarkerCategory[] newCategories = buildHierarchy(newMarkers, 0,
						newMarkers.getSize() - 1, 0);
				if (monitor.isCanceled())
					return;
				categories = newCategories;
			}

			lastMarkers = newMarkers;
			monitor.done();
		} finally {
			building = false;
		}

	}

	boolean isShowingHierarchy() {

		ViewerComparator sorter = view.getViewer().getComparator();
		if (sorter instanceof CategoryComparator) {
			return ((CategoryComparator) sorter).getCategoryField() != null;
		}
		return false;
	}

	MarkerCategory[] buildHierarchy(MarkerList markers, int start, int end,
			int sortIndex) {
		CategoryComparator sorter = getCategorySorter();

		if (sortIndex > 0) {
			return null;// Are we out of categories?
		}

		Collection categories = new ArrayList();

		Object previous = null;
		int categoryStart = start;

		Object[] elements = markers.getArray();

		for (int i = start; i <= end; i++) {

			if (previous != null) {
				if (sorter.compare(previous, elements[i], sortIndex, false) != 0) {
					categories.add(new MarkerCategory(this, categoryStart,
							i - 1, getNameForIndex(markers, categoryStart)));
					categoryStart = i;
				}
			}
			previous = elements[i];

		}

		if (end >= categoryStart) {
			categories.add(new MarkerCategory(this, categoryStart, end,
					getNameForIndex(markers, categoryStart)));
		}

		MarkerCategory[] nodes = new MarkerCategory[categories.size()];
		categories.toArray(nodes);
		return nodes;

	}

	private String getNameForIndex(MarkerList markers, int categoryStart) {
		return getCategorySorter().getCategoryField().getValue(
				markers.toArray()[categoryStart]);
	}

	public MarkerList getCurrentMarkers() {
		if (lastMarkers == null) {// First time?
			view.scheduleMarkerUpdate(Util.SHORT_DELAY);
			building = true;
		}
		if (building) {
			return new MarkerList();
		}
		return lastMarkers;
	}

	public Object[] getElements() {

		if (lastMarkers == null) {// First time?
			view.scheduleMarkerUpdate(Util.SHORT_DELAY);
			building = true;
		}
		if (building) {
			return Util.EMPTY_MARKER_ARRAY;
		}
		if (isShowingHierarchy() && categories != null) {
			return categories;
		}
		return lastMarkers.toArray();
	}

	public boolean hasNoMarkers() {
		return lastMarkers == null;
	}

	public MarkerCategory[] getCategories() {
		if (building) {
			return null;
		}
		return categories;
	}

	boolean isBuilding() {
		return building;
	}

}
