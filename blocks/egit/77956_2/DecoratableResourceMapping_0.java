			DecoratableResourceAdapter adapter = new DecoratableResourceAdapter(
					indexDiffData, mappingResource);
			if (adapter.isTracked()) {
				anyIsTracked = true;
			}
			if (adapter.hasConflicts()) {
				anyIsConflict = true;
			}
			if (adapter.isDirty()) {
				anyIsDirty = true;
			}
			stagingStates.add(adapter.getStagingState());
		}
