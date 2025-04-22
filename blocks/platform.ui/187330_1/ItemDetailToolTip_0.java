				Image warningImage;
				if (!effectivelyAvailable) {
					warningImage = dialog.warningImageDescriptor.createImage();
					destination.addDisposeListener(e -> warningImage.dispose());
				} else {
					warningImage = null;
				}
				Link link = createEntryWithLink(destination, warningImage, text);
