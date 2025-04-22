					continue;
				}
				if (item.isSeparator() || item.isGroupMarker()) {
					continue;
				}
				if (isChildVisible(item, manager)) {
					setChildVisible(toolBarElem, item, manager);
					container.setVisible(true);
