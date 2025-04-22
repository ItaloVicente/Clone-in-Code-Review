	protected void addItems(Table table, WorkbenchPage page) {
		IPerspectiveDescriptor perspectives[] = page.getSortedPerspectives();
        for (int i = perspectives.length - 1; i >= 0; i--) {
            TableItem item = new TableItem(table, SWT.NONE);
            IPerspectiveDescriptor desc = perspectives[i];
            String text = labelProvider.getText(desc);
            if(text == null) {
			}
            item.setText(text);
            item.setImage(labelProvider.getImage(desc));
            item.setData(desc);
        }

