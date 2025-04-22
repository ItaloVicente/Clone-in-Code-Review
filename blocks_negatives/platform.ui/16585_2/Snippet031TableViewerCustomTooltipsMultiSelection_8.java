		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof MyModel) {
				switch (columnIndex) {
					case 0: return ((MyModel)element).col1;
					case 1: return ((MyModel)element).col2;
				}
