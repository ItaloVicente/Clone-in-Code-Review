			updateCategories();
		}
		List children = getChildren(node);

		Set set = new HashSet(childItems.length * 2 + 1);

		for (int i = 0; i < childItems.length; i++) {
			Object data = childItems[i].getData();
			if (data != null) {
				Object e = data;
				int ix = children.indexOf(e);
				if (ix < 0) { // not found
					removeItem(childItems[i]);
				} else { // found
					set.add(e);
				}
			} else if (data == null) { // the dummy
				childItems[i].dispose();
			}
