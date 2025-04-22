			for (IProject project : projects) {
				includeList(project, indexDiff.getAdded(), indexChanges);
				includeList(project, indexDiff.getChanged(), indexChanges);
				includeList(project, indexDiff.getRemoved(), indexChanges);
				includeList(project, indexDiff.getMissing(), notIndexed);
				includeList(project, indexDiff.getModified(), notIndexed);
			}
