        perspName = text.getText();

        ignoreSelection = true;
        persp = perspReg.findPerspectiveWithLabel(perspName);
        if (persp == null) {
            StructuredSelection sel = new StructuredSelection();
            list.setSelection(sel);
        } else {
            StructuredSelection sel = new StructuredSelection(persp);
            list.setSelection(sel);
        }
        ignoreSelection = false;

        updateButtons();
    }

    /**
     * Notifies that the ok button of this dialog has been pressed.
     * <p>
     * The default implementation of this framework method sets
     * this dialog's return code to <code>Window.OK</code>
     * and closes the dialog. Subclasses may override.
     * </p>
     */
    @Override
