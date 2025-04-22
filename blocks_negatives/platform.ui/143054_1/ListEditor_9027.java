        if (list != null) {
            list.setFocus();
        }
    }

    /**
     * Moves the currently selected item up or down.
     *
     * @param up <code>true</code> if the item should move up,
     *  and <code>false</code> if it should move down
     */
    private void swap(boolean up) {
        setPresentsDefaultValue(false);
        int index = list.getSelectionIndex();
        int target = up ? index - 1 : index + 1;

        if (index >= 0) {
            String[] selection = list.getSelection();
            Assert.isTrue(selection.length == 1);
            list.remove(index);
            list.add(selection[0], target);
            list.setSelection(target);
        }
        selectionChanged();
    }

    /**
     * Notifies that the Up button has been pressed.
     */
    private void upPressed() {
        swap(true);
    }

    /*
     * @see FieldEditor.setEnabled(boolean,Composite).
     */
    @Override
