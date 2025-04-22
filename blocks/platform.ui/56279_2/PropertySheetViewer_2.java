				Rectangle area = tree.getClientArea();
				TreeColumn[] columns = tree.getColumns();
				if (area.width > 0) {
					columns[0].setWidth(area.width * 40 / 100);
					columns[1].setWidth(area.width - columns[0].getWidth() - 4);
					tree.removeControlListener(this);
				}
			}
		});

	}

	private void applyEditorValue() {
		TreeItem treeItem = treeEditor.getItem();
		if (treeItem == null || treeItem.isDisposed()) {
