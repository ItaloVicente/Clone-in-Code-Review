        Control control = getControl();
        if (control != null) {
            control.setSize(uiSize);
            size = uiSize;
        }
    }

    /**
     * The <code>PreferencePage</code> implementation of this <code>IDialogPage</code>
     * method extends the <code>DialogPage</code> implementation to update
     * the preference page container title. Subclasses may extend.
     * @see IDialogPage#setTitle(String)
     */
    @Override
