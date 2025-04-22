			}
			Object d = widget.getData();
			if (d != null) {
				Object parentElement = d;
				Object[] children;
				if (isTreePathContentProvider() && widget instanceof Item) {
					TreePath path = getTreePathFromItem((Item) widget);
					children = getSortedChildren(path);
				} else {
					children = getSortedChildren(parentElement);
