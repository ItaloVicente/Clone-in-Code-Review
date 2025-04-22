                () -> {
				    Iterator keyIterator = items.keySet().iterator();

				    while (keyIterator.hasNext()) {
				        Object key1 = keyIterator.next();
				        List selections = (List) items.get(key1);
				        if (selections.size() == 0) {
				            checkedStateStore.remove(key1);
						} else {
				            checkedStateStore.put(key1, selections);
				            Object parent = treeContentProvider
				                    .getParent(key1);
				            if (parent != null) {
				                addToHierarchyToCheckedStore(parent);
				            }
				        }
				    }

				    keyIterator = items.keySet().iterator();

				    while (keyIterator.hasNext()) {
				        Object key2 = keyIterator.next();
				        updateHierarchy(key2);
				        if (currentTreeSelection != null
				                && currentTreeSelection.equals(key2)) {
				            listViewer.setAllChecked(false);
				            listViewer.setCheckedElements(((List) items
				                    .get(key2)).toArray());
				        }
				    }
				});
