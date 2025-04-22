				includeList(project, indexDiff.getAdded(), Collections
						.<IFile> emptyList(), candidates);
				includeList(project, indexDiff.getChanged(), Collections
						.<IFile> emptyList(), candidates);
				includeList(project, indexDiff.getRemoved(), Collections
						.<IFile> emptyList(), candidates);
				includeList(project, indexDiff.getMissing(), notIndexedFiles,
						candidates);
				includeList(project, indexDiff.getModified(), notIndexedFiles,
						candidates);
				addUntrackedFiles(repository, project, notTrackedFiles,
						candidates);
