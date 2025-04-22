		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			return ((Person) element)
					.getValue(viewer.getColumnProperties()[columnIndex]
							.toString());
