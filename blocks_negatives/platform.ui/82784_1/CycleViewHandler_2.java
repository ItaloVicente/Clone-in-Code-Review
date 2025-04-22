	private void createEditorItem(Table table, WorkbenchPage page, MPart part) {
		Object object = part.getObject();
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(WorkbenchMessages.CyclePartAction_editor);
		if (object instanceof CompatibilityEditor) {
			IEditorPart editor = ((CompatibilityEditor) object).getEditor();
			item.setImage(editor.getTitleImage());
			if (editor.getSite() instanceof PartSite) {
				item.setData(((PartSite) editor.getSite()).getPartReference());
				return;
			}
		} else {
			item.setImage(getImage(page, part));
