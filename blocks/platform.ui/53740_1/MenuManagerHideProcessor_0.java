
						if (mel != null && mel.size() > 0) {
							for (MMenuElement item : mel) {
								item.setVisible(false);
							}
						}
						currentMenuElement.getTransientData()
								.remove(MenuManagerShowProcessor.DYNAMIC_ELEMENT_STORAGE_KEY);
