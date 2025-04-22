			}
			IResourceState baseState = ResourceStateFactory.getInstance()
					.get(indexDiffData, resourceToWrap);
			setTracked(baseState.isTracked());
			setIgnored(baseState.isIgnored());
			setDirty(baseState.isDirty());
			setConflicts(baseState.hasConflicts());
			setAssumeValid(baseState.isAssumeValid());
			setStaged(baseState.staged());
			if (resource.getType() == IResource.PROJECT) {
