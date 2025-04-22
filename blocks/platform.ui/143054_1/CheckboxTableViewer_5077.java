		return false;
	}

	public void setCheckedElements(Object[] elements) {
		assertElementsNotNull(elements);
		CustomHashtable set = newHashtable(elements.length * 2 + 1);
		for (Object element : elements) {
			set.put(element, element);
		}
		TableItem[] items = getTable().getItems();
		for (TableItem item : items) {
			Object element = item.getData();
			if (element != null) {
				boolean check = set.containsKey(element);
				if (item.getChecked() != check) {
					item.setChecked(check);
				}
			}
		}
	}

	public boolean setGrayed(Object element, boolean state) {
		Assert.isNotNull(element);
		Widget widget = findItem(element);
