			Image handleImage = createImage(toFrame, classId,
					HANDLE_IMAGE_PROP, null);
			if (vertical && handleImage != null) {
				handleImage = rotateImage(toFrame.getDisplay(),
						handleImage, null);
			}
			if (frameImage != null) {
				frame.setImages(frameImage, frameInts, handleImage);
			}
		 };
