                new Runnable() {
                    @Override
					public void run() {
                        Iterator keyIterator = items.keySet().iterator();

                        while (keyIterator.hasNext()) {
                            Object key = keyIterator.next();
                            List selections = (List) items.get(key);
                            if (selections.size() == 0) {
                                checkedStateStore.remove(key);
							} else {
                                checkedStateStore.put(key, selections);
                                Object parent = treeContentProvider
                                        .getParent(key);
                                if (parent != null) {
                                    addToHierarchyToCheckedStore(parent);
                                }
                            }
                        }

                        keyIterator = items.keySet().iterator();

                        while (keyIterator.hasNext()) {
                            Object key = keyIterator.next();
                            updateHierarchy(key);
                            if (currentTreeSelection != null
                                    && currentTreeSelection.equals(key)) {
                                listViewer.setAllChecked(false);
                                listViewer.setCheckedElements(((List) items
                                        .get(key)).toArray());
                            }
                        }
                    }
                });
