package org.eclipse.ui.views.markers.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class MarkerList {
	
	private int[] markerCounts = null;

	private ConcreteMarker[] markers;

	private Map markerTable;

	public MarkerList() {
		this(new ConcreteMarker[0]);
	}

	public MarkerList(Collection markers) {
		this((ConcreteMarker[]) markers.toArray(new ConcreteMarker[markers
				.size()]));
	}

	public MarkerList(ConcreteMarker[] markers) {
		this.markers = markers;
	}

	public void clearCache() {
		for (int i = 0; i < markers.length; i++) {
			ConcreteMarker marker = markers[i];

			marker.clearCache();
		}

		markerTable = null;
	}

	private Map getMarkerMap() {
		if (markerTable == null) {
			markerTable = new HashMap();

			for (int idx = 0; idx < markers.length; idx++) {
				ConcreteMarker marker = markers[idx];
				markerTable.put(marker.getMarker(), marker);
			}
		}

		return markerTable;
	}

	public ConcreteMarker getMarker(IMarker toFind) {
		return (ConcreteMarker) getMarkerMap().get(toFind);
	}
	
	public IMarker[] getIMarkers(){
		IMarker[] iMarkers = new IMarker[markers.length];
		for (int i = 0; i < markers.length; i++) {
			iMarkers[i] = markers[i].getMarker();			
		}
		return iMarkers;
	}

	public static ConcreteMarker createMarker(IMarker marker)
			throws CoreException {
		if (marker.isSubtypeOf(IMarker.TASK)) {
			return new TaskMarker(marker);
		} else if (marker.isSubtypeOf(IMarker.BOOKMARK)) {
			return new BookmarkMarker(marker);
		} else if (marker.isSubtypeOf(IMarker.PROBLEM)) {
			return new ProblemMarker(marker);
		} else {
			return new ConcreteMarker(marker);
		}
	}

	public void refresh() {
		for (int markerIdx = 0; markerIdx < markers.length; markerIdx++) {
			ConcreteMarker next = markers[markerIdx];
			next.refresh();
		}
	}

	public List asList() {
		return Arrays.asList(markers);
	}

	public MarkerList findMarkers(Collection ofIMarker) {
		List result = new ArrayList(ofIMarker.size());

		Iterator iter = ofIMarker.iterator();
		while (iter.hasNext()) {
			IMarker next = (IMarker) iter.next();

			ConcreteMarker marker = getMarker(next);
			if (marker != null) {
				result.add(marker);
			}
		}

		return new MarkerList(result);
	}

	public static ConcreteMarker[] createMarkers(IMarker[] source)
			throws CoreException {
		ConcreteMarker[] result = new ConcreteMarker[source.length];

		for (int idx = 0; idx < source.length; idx++) {
			result[idx] = createMarker(source[idx]);
		}

		return result;
	}

	public static MarkerList compute(MarkerFilter[] filters,
			IProgressMonitor mon, boolean ignoreExceptions)
			throws CoreException {

		Collection returnMarkers = new HashSet();// avoid duplicates

		for (int i = 0; i < filters.length; i++) {
			returnMarkers.addAll(filters[i].findMarkers(mon, ignoreExceptions));
		}
		return new MarkerList(returnMarkers);
	}

	public static IMarker[] compute(String[] types) throws CoreException {

		ArrayList result = new ArrayList();
		IResource input = ResourcesPlugin.getWorkspace().getRoot();

		for (int i = 0; i < types.length; i++) {
			IMarker[] newMarkers = input.findMarkers(types[i], true,
					IResource.DEPTH_INFINITE);
			result.addAll(Arrays.asList(newMarkers));
		}

		return (IMarker[]) result.toArray(new IMarker[result.size()]);
	}

	public ConcreteMarker[] toArray() {
		return markers;
	}

	public int getItemCount() {
		return markers.length;
	}

	public int getErrors() {
		return getMarkerCounts()[IMarker.SEVERITY_ERROR];
	}

	public int getInfos() {
		return getMarkerCounts()[IMarker.SEVERITY_INFO];
	}

	public int getWarnings() {
		return getMarkerCounts()[IMarker.SEVERITY_WARNING];
	}

	private int[] getMarkerCounts() {
		if (markerCounts == null) {
			markerCounts = new int[] { 0, 0, 0 };

			for (int idx = 0; idx < markers.length; idx++) {
				ConcreteMarker marker = markers[idx];

				if (marker instanceof ProblemMarker) {
					int severity = ((ProblemMarker) markers[idx]).getSeverity();
					if (severity >= 0 && severity <= 2) {
						markerCounts[severity]++;
					}
				}

			}
		}
		return markerCounts;
	}

	public Object[] getArray() {
		return markers;
	}

	public int getSize() {
		return getArray().length;
	}

	public ConcreteMarker getMarker(int index) {
		return markers[index];
	}

	public void updateMarkers(Collection addedMarkers,Collection removedMarkers) {
		List list = new ArrayList(asList());
		list.addAll(addedMarkers);		
		list.removeAll(removedMarkers);	
		markers = new ConcreteMarker[list.size()];
		list.toArray(markers);
	}
	
	public void refreshAll() {
		for (int i = 0; i < markers.length; i++) {
			markers[i].refresh();
		}		
	}

	public void clearGroups() {
		for (int i = 0; i < markers.length; i++) {
			markers[i].setGroup(null);
		}
		
	}
}
