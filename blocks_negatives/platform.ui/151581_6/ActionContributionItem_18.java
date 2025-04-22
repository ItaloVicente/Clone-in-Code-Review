					if (text != null && acceleratorText == null) {
						acceleratorText = LegacyActionTools
								.extractAcceleratorText(text);
						if (acceleratorText == null && accelerator != 0) {
							acceleratorText = Action
									.convertAccelerator(accelerator);
						}
					}
