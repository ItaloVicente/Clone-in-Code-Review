			boolean checked = checkedStateStore.containsKey(currentElement);
			treeViewer.setChecked(currentElement, checked);
			treeViewer.setGrayed(currentElement, checked
					&& !whiteCheckedTreeItems.contains(currentElement));
		}
	}

	@Override
