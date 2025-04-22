							if (mel != null && mel.size() > 0) {
								renderer.removeDynamicMenuContributions(
										menuManager, menuModel, mel);
							}

							storageMap
									.remove(MenuManagerShowProcessor.DYNAMIC_ELEMENT_STORAGE_KEY);
						}
