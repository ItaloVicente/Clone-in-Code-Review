        }
        removedVariableNames.clear();
    }

    /**
     * Updates button enabled state, depending on the number of currently selected
     * variables in the table.
     */
    private void updateEnabledState() {
        int itemsSelectedCount = variableTable.getTable().getSelectionCount();
        editButton.setEnabled(itemsSelectedCount == 1 && canChangeSelection());
        removeButton.setEnabled(itemsSelectedCount > 0 && canChangeSelection());
    }
