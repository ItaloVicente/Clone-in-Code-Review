			for (; i < filteredElements.length; i++) {
				Object item = filteredElements[i];
				if (hasPerfectMatch && !filter.getPattern().equals(getElementName(item))) {
					if (isHistoryElement(item)) {
						hasHistory = true;
						setSeparatorLabel(WorkbenchMessages.FilteredItemsSelectionDialog_separatorLabel_history);
						preparedElements.add(itemsListSeparator);
					} else {
						setSeparatorLabel(WorkbenchMessages.FilteredItemsSelectionDialog_separatorLabel_workspace);
						preparedElements.add(itemsListSeparator);
					}
				}

				if (filter != null && filter.getPattern().equals(getElementName(item))) {
					hasPerfectMatch = true;
					preparedElements.add(item);
				} else {
					break;
				}
			}

			if (!hasPerfectMatch && filteredElements.length > 0) {
