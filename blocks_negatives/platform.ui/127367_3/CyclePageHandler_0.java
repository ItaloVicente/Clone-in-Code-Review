	protected void addItemz(Table table, WorkbenchPage page) {
		for (Object availablePage : pageSwitcher.getPages()) {
			TableItem item = null;
			item = new TableItem(table, SWT.NONE);
			ImageDescriptor imageDescriptor = pageSwitcher
					.getImageDescriptor(availablePage);
			if (imageDescriptor != null) {
				if (lrm == null) {
					lrm = new LocalResourceManager(JFaceResources
							.getResources());
				}
				item.setImage(lrm.createImage(imageDescriptor));
			}
			item.setData(availablePage);
			String name = pageSwitcher.getName(availablePage);
			if (name.length() > TEXT_LIMIT) {
				name = name.substring(0, TEXT_LIMIT) + "..."; //$NON-NLS-1$
			}
			item.setText(name);
		}
	}

