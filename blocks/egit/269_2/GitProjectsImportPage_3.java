				checkedItems.clear();
				for (final TreeItem item : projectsList.getTree().getItems()) {
					item.setChecked(true);
					checkedItems.add(item.getData());
				}
				setPageComplete(true);
