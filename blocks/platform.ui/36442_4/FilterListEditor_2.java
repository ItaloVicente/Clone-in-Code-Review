			String filter = dialog.getFilter();
			List list = getList();
			if (list.getItemCount() != 0) {
				int pos = Arrays.binarySearch(list.getItems(), filter);
				if (pos >= 0) {
					return null;  // Identical item already exists.
				}
				list.setSelection(-pos - 2);
			}
			return filter;
