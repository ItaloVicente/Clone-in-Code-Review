		if (vertical && frameImage != null) {
			Image frameImageRotated = createImage(toFrame, classId, FRAME_IMAGE_ROTATE_PROP, frameInts);
			if (frameImageRotated != null) {
				frameImage = frameImageRotated;
				if (frameInts != null) {
					int tmp;
					tmp = frameInts[0];
					frameInts[0] = frameInts[2];
					frameInts[2] = tmp;
					tmp = frameInts[1];
					frameInts[1] = frameInts[3];
					frameInts[3] = tmp;
				}
			} else {
				frameImage = rotateImage(toFrame.getDisplay(), frameImage, frameInts);
			}
		}
