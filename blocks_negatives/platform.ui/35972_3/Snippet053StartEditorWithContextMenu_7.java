			@Override
			public void modify(Object element, String property, Object value) {
				TreeItem item = (TreeItem) element;
				((MyModel) item.getData()).counter = Integer.parseInt(value.toString());
				viewer.update(item.getData(), null);
			}
