					Object d = widget.getData();
					if (d != null) {
						Object parentElement = d;
						Object[] children;
						if (isTreePathContentProvider() && widget instanceof Item) {
							TreePath path = getTreePathFromItem((Item) widget);
							children = getSortedChildren(path);
						} else {
							children = getSortedChildren(parentElement);
						}
						for (int i = 0; i < children.length; i++) {
							createTreeItem(widget, children[i], -1);
						}
