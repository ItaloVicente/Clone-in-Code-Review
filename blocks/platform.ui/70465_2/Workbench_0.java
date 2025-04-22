				String value = System.getProperty("swt.enable.autoScale"); //$NON-NLS-1$
				final boolean autoScaleEnable;
				if (value != null && "false".equalsIgnoreCase(value)) { //$NON-NLS-1$
					autoScaleEnable = false;
				} else {
					autoScaleEnable = true;
				}
				final ImageData data = new ImageData(input);
				background = new Image(display, new ImageDataProvider() {
					@Override
					public ImageData getImageData(int zoom) {
						String deviceZoomStr = System.getProperty("org.eclipse.swt.internal.deviceZoom"); //$NON-NLS-1$
						int currentDeviceZoom = deviceZoomStr != null ? Integer.parseInt(deviceZoomStr) : 100;
						float scaleFactor = autoScaleEnable ? ((float) zoom / currentDeviceZoom) : 1f;
						return scaleFactor == 1f ? data
								: data.scaledTo(Math.round(data.width * scaleFactor),
										Math.round(data.height * scaleFactor));
					}
				});
