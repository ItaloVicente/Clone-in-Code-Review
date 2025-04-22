				String remId = ""; //$NON-NLS-1$
				try {
					remId = id.substring(colonIndex + 1);
				} catch (StringIndexOutOfBoundsException e) {
				}
				if (!remId.trim().equals("*")) {//$NON-NLS-1$
					for (MUIElement element : sharedWindow.getSharedElements()) {
						if (element.getElementId().equals(descId)) {
							sharedPart = (MPart) element;
							MPlaceholder ph = sharedPart.getCurSharedRef();
							if (ph != null) {
								sharedPlaceHolderParent = ph.getParent();
							}
							break;
