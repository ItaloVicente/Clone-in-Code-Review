				for (MUIElement element : sharedWindow.getSharedElements()) {
					if (element.getElementId().equals(descId)) {
						sharedPart = (MPart) element;
						MPlaceholder ph = sharedPart.getCurSharedRef();
						if (ph != null) {
							sharedPlaceHolderParent = ph.getParent();
