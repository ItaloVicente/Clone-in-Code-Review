					if (overlayImage != null) {
						return overlayImage;
					}
					ImageDescriptor natureImage = IDEWorkbenchPlugin
							.getDefault().getProjectImageRegistry()
							.getNatureImage(imageKey);
					if (natureImage != null) {
