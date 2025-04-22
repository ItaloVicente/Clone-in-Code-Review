        super.setToolTipText(text);
        defaultToolTipText = text;
    }

    /**
     * Ensures the accelerator is correct in the text (handlers are not
     * allowed to change the accelerator).
     */
    private String appendAccelerator(String newText) {
        if (newText == null) {
