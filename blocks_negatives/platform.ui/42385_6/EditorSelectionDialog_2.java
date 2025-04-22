			TableItem ti = new TableItem(editorTable, SWT.NULL);
			ti.setData(editor);
			ti.setText(editor.getLabel());
			Image image = (Image) resourceManager.get(editor.getImageDescriptor());
			ti.setImage(image);

			editorTable.setSelection(new TableItem[] { ti });
			editorTable.showSelection();
			editorTable.setFocus();
			selectedEditor = editor;
