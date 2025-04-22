	protected Object getInput(WorkbenchPage page) {
		List<FilteredTableItem> rows = new ArrayList<>();

		for(int i=0; i<pageSwitcher.getPages().length; i++){
			Object viewPage = pageSwitcher.getPages()[i];
			FilteredTableItem item = new FilteredTableItem();
			ImageDescriptor imageDescriptor = pageSwitcher.getImageDescriptor(viewPage);
			if (imageDescriptor != null) {
				if (lrm == null) {
					lrm = new LocalResourceManager(JFaceResources.getResources());
				}
				item.setImage(lrm.createImage(imageDescriptor));
			}
			item.putData(K_INDEX, i);
			String name = pageSwitcher.getName(viewPage);
			if (name.length() > TEXT_LIMIT) {
				name = name.substring(0, TEXT_LIMIT) + "..."; //$NON-NLS-1$
			}
			item.setText(name);
			rows.add(item);
		}
		return rows;
	}

	protected void addItemz(Table table, WorkbenchPage page) {
