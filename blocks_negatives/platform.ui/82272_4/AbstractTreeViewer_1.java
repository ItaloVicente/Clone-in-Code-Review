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
					for (int i2 = 0; i2 < children.length; i2++) {
						createTreeItem(widget, children[i2], -1);
					}
