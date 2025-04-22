		projectsList.setLabelProvider(new LabelProvider() {
			public String getText(Object element) {
				for (final TreeItem item : projectsList.getTree().getItems()) {
					if (checkedItems.contains(item.getData()))
						item.setChecked(true);
					else
						item.setChecked(false);
				}
				return ((ProjectRecord) element).getProjectLabel();
