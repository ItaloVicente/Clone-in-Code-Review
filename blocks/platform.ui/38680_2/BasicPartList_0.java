			CTabItem cti = findItemForPart((MUIElement) element);
			if (cti != null) {
				Image image = cti.getImage();
				if (image != null && !image.isDisposed()) {
					return image;
				}
			}

