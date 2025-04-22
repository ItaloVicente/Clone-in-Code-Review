				Image handleImageRotated = createImage(toFrame, classId, HANDLE_IMAGE_ROTATE_PROP, null);
				if (handleImageRotated != null) {
					handleImage = handleImageRotated;
				} else {
					handleImage = rotateImage(toFrame.getDisplay(), handleImage, null);
				}
