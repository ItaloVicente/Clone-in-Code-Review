			if (sharedPart == null) {
				for (MUIElement element : sharedWindow.getSharedElements()) {
					if (element.getElementId().equals(descId)) {
						sharedPart = (MPart) element;
						break;
					}
