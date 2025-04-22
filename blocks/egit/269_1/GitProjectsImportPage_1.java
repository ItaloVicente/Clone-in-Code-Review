		projectsList.setLabelProvider(new ITableLabelProvider() {

			public void removeListener(ILabelProviderListener listener) {
			}

			public boolean isLabelProperty(Object element, String property) {
				return false;
			}

			public void dispose() {
			}

			public void addListener(ILabelProviderListener listener) {
			}

			public String getColumnText(Object element, int columnIndex) {
				if (checkedItems.contains(element))
					for (final TreeItem item : projectsList.getTree()
							.getItems()) {
						if (element == item.getData()) // not equals
							item.setChecked(true);
					}
				return element.toString();
			}

			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}
		});
