			@Override
			public void modify(Object element, String property, Object value) {
				((MyModel) ((TreeItem) element).getData()).counter = Integer
						.parseInt(value.toString());
				viewer.update(((TreeItem) element).getData(), null);
			}
