							checkedStateStore.put(key1, selections);
							Object parent = treeContentProvider
									.getParent(key1);
							if (parent != null) {
								addToHierarchyToCheckedStore(parent);
							}
						}
					}

