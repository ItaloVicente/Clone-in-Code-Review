
		public TreeItem getItem(Object element) {
			Widget item = findItem(element);
			if (item instanceof TreeItem) {
				return (TreeItem) item;
			}
			return null;
		}
