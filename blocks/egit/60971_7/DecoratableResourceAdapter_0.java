			}
			IResourceState baseState = ResourceStateFactory.getInstance()
					.get(indexDiffData, resourceToWrap);
			setTracked(baseState.isTracked());
			setIgnored(baseState.isIgnored());
			setDirty(baseState.isDirty());
			setConflicts(baseState.hasConflicts());
			setAssumeValid(baseState.isAssumeValid());
			setStagingState(baseState.getStagingState());
			if (resource.getType() == IResource.PROJECT) {
