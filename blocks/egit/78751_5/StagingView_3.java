			}
		}
		for (Object element : getVisibleExpandedElements(viewer)) {
			if (element instanceof StagingFolderEntry) {
				addPathAndParentPaths(((StagingFolderEntry) element).getPath(),
						paths);
			}
		}

