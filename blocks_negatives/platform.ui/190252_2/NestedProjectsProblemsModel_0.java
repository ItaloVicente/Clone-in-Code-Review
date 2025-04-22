			IPath currentLocation = currentContainer.getLocation();
			if (currentLocation == null) {
				continue;
			}
			dirtyLeafContainers
					.removeIf(leafContainer -> {
						IPath leafLocation = leafContainer.getLocation();
						return leafLocation != null && leafLocation.isPrefixOf(currentLocation);
					});
			if (dirtyLeafContainers.stream().noneMatch(
					leafContainer -> {
						IPath leafLocation = leafContainer.getLocation();
						return leafLocation != null && currentLocation.isPrefixOf(leafLocation);
					})) {
