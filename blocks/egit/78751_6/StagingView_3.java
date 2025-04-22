			}
		}
		for (Object element : viewer.getVisibleExpandedElements()) {
			if (element instanceof StagingFolderEntry) {
				addPathAndParentPaths(((StagingFolderEntry) element).getPath(),
						paths);
			}
		}

