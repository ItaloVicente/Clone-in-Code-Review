		ProjectTrackerMemento memento = new ProjectTrackerMemento();

		ProjectTrackerPreferenceSnapshot snapshot = takeSnapshot();
		if (snapshot != null) {
			memento.addSnapshot(snapshot);
		}

		return memento;
