package org.eclipse.egit.ui.internal.branch;

import java.util.ArrayList;
import java.util.Collection;

class ProjectTrackerMemento {

	private Collection<ProjectTrackerPreferenceSnapshot> snapshots;

	ProjectTrackerMemento() {
		snapshots = new ArrayList<>();
	}

	void addSnapshot(ProjectTrackerPreferenceSnapshot snapshot) {
		snapshots.add(snapshot);
	}

	Collection<ProjectTrackerPreferenceSnapshot> getSnapshots() {
		return snapshots;
	}
}
