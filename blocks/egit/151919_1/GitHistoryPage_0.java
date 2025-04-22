			boolean headChanged = !headId.equals(currentHeadId);
			boolean pathsChanged = pathChanged(pathFilters, paths);
			boolean settingsChanged = updateSettings();
			boolean fetchHeadChanged = currentShowAdditionalRefs
					&& fetchHeadId != null
					&& !fetchHeadId.equals(currentFetchHeadId);
			if (forceNewWalk || repoChanged || objChanged || headChanged
					|| fetchHeadChanged || pathsChanged || settingsChanged) {
