			if (sharedPart == null) {
				int colonIndex = id.indexOf(':');
				if (colonIndex >= 0) {
					secondaryId = true;
					descId = id.substring(0, colonIndex);
					descId += ":*"; //$NON-NLS-1$
				}
				for (MUIElement element : sharedWindow.getSharedElements()) {
					if (element.getElementId().equals(descId)) {
						sharedPart = (MPart) element;
						break;
					}
				}
			}
