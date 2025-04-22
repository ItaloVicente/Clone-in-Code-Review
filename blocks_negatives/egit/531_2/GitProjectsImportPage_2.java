				for (final TreeItem item : projectsList.getTree().getItems()) {
					if (checkedItems.contains(item.getData()))
						item.setChecked(true);
					else
						item.setChecked(false);
				}
				return ((ProjectRecord) element).getProjectLabel();
