				boolean elementVisible = super
						.isElementVisible(viewer, element);
				if (getCheckedItems().contains(element)) {
					if (!fUserModifiedTreeSelection) {
						fTreeViewer.setChecked(element, elementVisible);
					} else {
						return true;
					}
				}
				return elementVisible;
