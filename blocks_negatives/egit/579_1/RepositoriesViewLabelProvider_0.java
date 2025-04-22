					CompositeImageDescriptor cd = new CompositeImageDescriptor() {

						@Override
						protected Point getSize() {
							return new Point(image.getBounds().width, image
									.getBounds().width);
						}

						@Override
						protected void drawCompositeImage(int width, int height) {
							drawImage(image.getImageData(), 0, 0);
							drawImage(UIIcons.OVR_CHECKEDOUT.getImageData(), 0,
									0);

						}
					};
					return cd.createImage();
