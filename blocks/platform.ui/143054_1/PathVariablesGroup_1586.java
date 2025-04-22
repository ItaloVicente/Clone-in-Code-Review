		}
		removedVariableNames.clear();
	}

	private void updateEnabledState() {
		int itemsSelectedCount = variableTable.getTable().getSelectionCount();
		editButton.setEnabled(itemsSelectedCount == 1 && canChangeSelection());
		removeButton.setEnabled(itemsSelectedCount > 0 && canChangeSelection());
	}
