			}
			internalCollectGrayed(result, item);
		}
	}

	private void internalSetChecked(CustomHashtable checkedElements,
			Widget widget) {
		Item[] items = getChildren(widget);
		for (Item child : items) {
			TreeItem item = (TreeItem) child;
			Object data = item.getData();
			if (data != null) {
				boolean checked = checkedElements.containsKey(data);
				if (checked != item.getChecked()) {
					item.setChecked(checked);
				}
			}
			internalSetChecked(checkedElements, item);
		}
	}

	private void internalSetGrayed(CustomHashtable grayedElements, Widget widget) {
		Item[] items = getChildren(widget);
		for (Item child : items) {
			TreeItem item = (TreeItem) child;
			Object data = item.getData();
			if (data != null) {
				boolean grayed = grayedElements.containsKey(data);
				if (grayed != item.getGrayed()) {
					item.setGrayed(grayed);
				}
			}
			internalSetGrayed(grayedElements, item);
		}
	}

	@Override
