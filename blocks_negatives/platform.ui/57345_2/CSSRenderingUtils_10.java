				Integer[] frameInts = new Integer[4];
				Image frameImage = createImage(toFrame, classId,
						FRAME_IMAGE_PROP, frameInts);
				if (vertical && frameImage != null) {
					frameImage = rotateImage(toFrame.getDisplay(), frameImage,
							frameInts);
				}
