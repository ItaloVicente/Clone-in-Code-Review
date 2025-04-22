						if (!item.getExpanded()) {
							item.setItemCount(1);
							TreeItem child = item.getItem(0);
							if (child.getData() != null) {
								disassociate(child);
							}
							item.clear(0, true);
						} else {
                            virtualLazyUpdateChildCount(item, item.getItemCount());
                        }
					}
