
								if (monitor.isCanceled()) {
									return Status.CANCEL_STATUS;
								}
								String elementId = orderedElements[i];
								monitor.subTask("loading quick access element with id \"" + elementId + "\""); //$NON-NLS-1$ //$NON-NLS-2$

								QuickAccessProvider quickAccessProvider = providerMap.get(orderedProviders[i]);
								int numTexts = Integer.parseInt(textEntries[i]);
								if (quickAccessProvider != null) {
									QuickAccessElement quickAccessElement = quickAccessProvider
											.getElementForId(elementId);
									if (quickAccessElement != null) {
										ArrayList<String> arrayList = new ArrayList<>();
										for (int j = arrayIndex; j < arrayIndex + numTexts; j++) {
											String text = textArray[j];
											if (text.length() > 0) {
												arrayList.add(text);
												elementMap.put(text, quickAccessElement);
											}
										}
										textMap.put(quickAccessElement, arrayList);
										previousPicksList.add(quickAccessElement);
									}
								}
								arrayIndex += numTexts;

								monitor.worked(1);
