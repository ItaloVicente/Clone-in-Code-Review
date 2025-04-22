            }
            invertedSelection.add(allItems[i]);
        }
        TableItem result[] = new TableItem[invertedSelection.size()];
        invertedSelection.toArray(result);
        return result;
    }

    /**
     * Updates the specified item
     */
    private void updateItem(TableItem item, Adapter editor) {
        item.setData(editor);
        item.setText(editor.getText());
        Image image = editor.getImage();
        if (image != null)
        	item.setImage(0, image);
    }

    /**
     * Adds all editors to elements
     */
    private void updateEditors(IWorkbenchPage[] pages) {
        for (IWorkbenchPage page : pages) {
