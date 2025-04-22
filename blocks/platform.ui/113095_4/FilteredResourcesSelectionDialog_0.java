		private StyledString styleResourceExtensionMatch(IResource resource, String matchingString, Styler styler) {
			StyledString str = new StyledString(resource.getName().trim());
			String resourceExtension = resource.getFileExtension();
			int lastDotIndex = matchingString.lastIndexOf('.');
			if (lastDotIndex == -1 || resourceExtension == null) {
				return str;
			}
			resourceExtension = '.' + resourceExtension;
			int resourceExtensionIndex = resource.getName().lastIndexOf(resourceExtension);
			String matchingExtension = matchingString.substring(lastDotIndex, matchingString.length());
			for (Position markPosition : getMatchPositions(resourceExtension, matchingExtension)) {
				str.setStyle(resourceExtensionIndex + markPosition.offset, markPosition.length, styler);
