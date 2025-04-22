				Image handleImage = createImage(toFrame, classId,
						HANDLE_IMAGE_PROP, null);
				if (vertical && handleImage != null) {
					handleImage = rotateImage(toFrame.getDisplay(),
							handleImage, null);
				}
				if (handleImage != null) {
					frame.setImages(null, null, handleImage);
				}
