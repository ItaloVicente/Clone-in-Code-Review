			IPath parentContainerAbsolutePath = location.removeLastSegments(1);
			IPath location2 = mostDirectParentProject.getLocation();
			if (location2 == null) {
				return null;
			}
			if (parentContainerAbsolutePath.equals(location2)) {
