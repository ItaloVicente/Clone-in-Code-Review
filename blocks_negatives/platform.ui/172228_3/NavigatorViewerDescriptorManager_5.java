						IConfigurationElement[] insertionPointElements = tagPopupMenu[0]
								.getChildren(TAG_INSERTION_POINT);
						MenuInsertionPoint[] insertionPoints = new MenuInsertionPoint[insertionPointElements.length];
						String name;
						String stringAttSeparator;

						boolean isSeparator;
						for (int indx = 0; indx < insertionPointElements.length; indx++) {
							name = insertionPointElements[indx]
									.getAttribute(ATT_NAME);
							stringAttSeparator = insertionPointElements[indx]
									.getAttribute(ATT_SEPARATOR);
							isSeparator = stringAttSeparator != null ? Boolean
									.valueOf(stringAttSeparator).booleanValue()
									: false;
							insertionPoints[indx] = new MenuInsertionPoint(name,
									isSeparator);
						}
						descriptor.setCustomInsertionPoints(insertionPoints);
