							if (partToActivate != null) {
								partService.hidePart(partToActivate);
							} else {
								ToolItem[] items = trimStackTB.getItems();
								for (int i = 0; i < items.length; i++) {
									if (items[i].getData() instanceof MPart) {
										partService.hidePart((MPart) items[i].getData());
									}
								}
							}
