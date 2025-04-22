		return false;
	}

	public void setGrayedElements(Object[] elements) {
		assertElementsNotNull(elements);
		CustomHashtable set = newHashtable(elements.length * 2 + 1);
		for (int i = 0; i < elements.length; ++i) {
			set.put(elements[i], elements[i]);
		}
		TableItem[] items = getTable().getItems();
		for (int i = 0; i < items.length; ++i) {
			TableItem item = items[i];
			Object element = item.getData();
			if (element != null) {
				boolean gray = set.containsKey(element);
				if (item.getGrayed() != gray) {
					item.setGrayed(gray);
				}
			}
		}
	}
