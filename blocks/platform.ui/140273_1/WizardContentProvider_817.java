			return list.toArray();
		} else if (parentElement instanceof AdaptableList) {
			AdaptableList aList = (AdaptableList) parentElement;
			Object[] children = aList.getChildren();
			ArrayList list = new ArrayList(children.length);
			for (Object element : children) {
				handleChild(element, list);
			}
			if (list.size() == 1 && list.get(0) instanceof WizardCollectionElement) {
				return getChildren(list.get(0));
			}
