				        expandedTreeNodes.add(item);
				        if (whiteCheckedTreeItems.contains(item)) {
				            Object[] children = treeContentProvider
				                    .getChildren(item);
				            for (int i = 0; i < children.length; ++i) {
				                if (!whiteCheckedTreeItems
				                        .contains(children[i])) {
				                    Object child = children[i];
				                    setWhiteChecked(child, true);
				                    treeViewer.setChecked(child, true);
				                    checkedStateStore.put(child,
				                            new ArrayList());
				                }
				            }

				            setListForWhiteSelection(item);
				        }
				    }
