			if (currentLocation != null) {
				dirtyLeafContainers.removeIf(leafContainer -> {
					IPath leafLocation = leafContainer.getLocation();
					return leafLocation != null && leafLocation.isPrefixOf(currentLocation);
				});
				if (dirtyLeafContainers.stream().noneMatch(leafContainer -> {
					IPath leafLocation = leafContainer.getLocation();
					return leafLocation != null && currentLocation.isPrefixOf(leafLocation);
				})) {
					dirtyLeafContainers.add(currentContainer);
				}
			} else {
				dirtyLeafContainers.removeIf( //
						leafContainer -> leafContainer.getFullPath().isPrefixOf(currentContainer.getFullPath()));
				if (dirtyLeafContainers.stream().noneMatch( //
						leafContainer -> currentContainer.getFullPath().isPrefixOf(leafContainer.getFullPath()))) {
					dirtyLeafContainers.add(currentContainer);
				}
