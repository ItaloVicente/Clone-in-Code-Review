
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			if (provider instanceof ITableLabelProvider) {
				return ((ITableLabelProvider) provider).getColumnImage(element,
						columnIndex);
			} else {
				if (columnIndex == 0) {
					return getImage(element);
				} else {
					return null;
				}
			}
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (provider instanceof ITableLabelProvider) {
				return ((ITableLabelProvider) provider).getColumnText(element,
						columnIndex);
			} else {
				if (columnIndex == 0) {
					return getText(element);
				} else {
					return null;
				}
			}
		}
