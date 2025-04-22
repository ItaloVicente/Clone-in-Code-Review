							if (lockDescriptor.isPresent()) {
								ImageData originalImageData = descriptor.getImageData(100);
								OverlayIcon overlay = new OverlayIcon(descriptor, lockDescriptor.get(),
										new Point(originalImageData.width, originalImageData.height));
								return manager.createImage(overlay);
							}
