		unsortedList.toArray(array);

		Collections.sort(Arrays.asList(array), comparer);
		return array;
	}

	private void sortInternalEditors() {
