			}
			invertedSelection.add(allItems[i]);
		}
		TableItem result[] = new TableItem[invertedSelection.size()];
		invertedSelection.toArray(result);
		return result;
	}

	private void updateItem(TableItem item, Adapter editor) {
		item.setData(editor);
		item.setText(editor.getText());
		Image image = editor.getImage();
		if (image != null)
			item.setImage(0, image);
	}

	private void updateEditors(IWorkbenchPage[] pages) {
		for (IWorkbenchPage page : pages) {
