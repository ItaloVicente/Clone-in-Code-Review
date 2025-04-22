		createChildren(item);
		Item[] items = getChildren(item);
		if (items != null) {
			for (Item child : items) {
				if (child.getData() != null && (child instanceof TreeItem)) {
					TreeItem treeItem = (TreeItem) child;
					treeItem.setChecked(state);
					setCheckedChildren(treeItem, state);
				}
			}
		}
	}

	public void setCheckedElements(Object[] elements) {
		assertElementsNotNull(elements);
		CustomHashtable checkedElements = newHashtable(elements.length * 2 + 1);
		for (Object element : elements) {
			internalExpand(element, false);
			checkedElements.put(element, element);
		}
		Control tree = getControl();
		tree.setRedraw(false);
		internalSetChecked(checkedElements, tree);
		tree.setRedraw(true);
	}

	public boolean setGrayed(Object element, boolean state) {
		Assert.isNotNull(element);
		Widget widget = internalExpand(element, false);
		if (widget instanceof TreeItem) {
			((TreeItem) widget).setGrayed(state);
			return true;
		}
		return false;
	}

	public boolean setGrayChecked(Object element, boolean state) {
		Assert.isNotNull(element);
		Widget widget = internalExpand(element, false);
		if (widget instanceof TreeItem) {
			TreeItem item = (TreeItem) widget;
			item.setChecked(state);
			item.setGrayed(state);
			return true;
		}
		return false;
	}

	public void setGrayedElements(Object[] elements) {
		assertElementsNotNull(elements);
		CustomHashtable grayedElements = newHashtable(elements.length * 2 + 1);
		for (Object element : elements) {
			internalExpand(element, false);
			grayedElements.put(element, element);
		}
		Control tree = getControl();
		tree.setRedraw(false);
		internalSetGrayed(grayedElements, tree);
		tree.setRedraw(true);
	}

	public boolean setParentsGrayed(Object element, boolean state) {
		Assert.isNotNull(element);
		Widget widget = internalExpand(element, false);
		if (widget instanceof TreeItem) {
			TreeItem item = (TreeItem) widget;
			item.setGrayed(state);
			item = item.getParentItem();
			while (item != null) {
				item.setGrayed(state);
				item = item.getParentItem();
			}
			return true;
		}
		return false;
	}

	public boolean setSubtreeChecked(Object element, boolean state) {
		Widget widget = internalExpand(element, false);
		if (widget instanceof TreeItem) {
			TreeItem item = (TreeItem) widget;
			item.setChecked(state);
			setCheckedChildren(item, state);
			return true;
		}
		return false;
	}

