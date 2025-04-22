    /**
     * Adds a mnemonic accelerator to actions in the MRU list of
     * recently built working sets.
     * @param action the action to add
     * @param index the index to add it at
     */
    private void addMnemonic(BuildSetAction action, int index) {
        StringBuilder label = new StringBuilder();
        if (index < 9) {
            label.append('&');
            label.append(index);
            label.append(' ');
        }
        label.append(action.getWorkingSet().getLabel());
        action.setText(label.toString());
    }
