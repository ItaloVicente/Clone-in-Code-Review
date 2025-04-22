				boolean elementVisible = super
						.isElementVisible(viewer, element);
				if (getCheckedItems().contains(element)) {
					if (!isUserModifiedTreeSelection)
						fTreeViewer.setChecked(element, elementVisible);
					else
						return true;
				}
				return elementVisible;
