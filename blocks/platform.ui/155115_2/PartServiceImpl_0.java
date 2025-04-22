				for (MUIElement element : sharedWindow.getSharedElements()) {
					if (element.getElementId().equals(descId)) {
						sharedPart = (MPart) element;
						MPlaceholder ph = sharedPart.getCurSharedRef();
						if (ph != null) {
							sharedPlaceHolderParent = ph.getParent();
						}
						break;
					}
				}
				if (sharedPart == null) {
					secondaryId = true;
					descId = id.substring(0, colonIndex);
					descId += ":*"; //$NON-NLS-1$
				}
