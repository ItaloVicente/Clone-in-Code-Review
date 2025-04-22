		return false;
	}

	public void setGrayedElements(Object[] elements) {
		assertElementsNotNull(elements);
		CustomHashtable set = newHashtable(elements.length * 2 + 1);
		for (Object element : elements) {
			set.put(element, element);
		}
		TableItem[] items = getTable().getItems();
		for (TableItem item : items) {
			Object element = item.getData();
			if (element != null) {
				boolean gray = set.containsKey(element);
				if (item.getGrayed() != gray) {
					item.setGrayed(gray);
				}
			}
		}
	}
