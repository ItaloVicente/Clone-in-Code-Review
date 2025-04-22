					ImageDescriptor imageDescriptor = AbstractUIPlugin
							.imageDescriptorFromPlugin(contentDescriptor
							.getContribution().getPluginId(), iconPath);
					if (imageDescriptor != null) {
						image = imageDescriptor.createImage();
						if (image != null) {
							getImageRegistry().put(iconKey, image);
						}
					}
