			Image image = getImage(element, resourceManager);
			Rectangle imageRect = image.getBounds();
			Rectangle textBounds = textLayout.getBounds();
			int iconSize = imageRect.height;
			if (iconSize > 16 && iconSize > 2 * textBounds.height) {
				iconSize = textBounds.height;
			}
			event.height = Math.max(event.height, iconSize + 3);
			event.width += iconSize + 4;
