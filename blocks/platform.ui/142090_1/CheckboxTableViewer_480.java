			}
		}

		super.preservingSelection(updateCode);

		children = getTable().getItems();
		for (TableItem item : children) {
			Object data = item.getData();
			if (data != null) {
				item.setChecked(checked.containsKey(data));
				item.setGrayed(grayed.containsKey(data));
			}
		}
	}

	@Override
