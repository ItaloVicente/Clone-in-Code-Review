        return isValid();
    }

    /**
     * Performs special processing when this page's Apply button has been pressed.
     * <p>
     * This is a framework hook method for subclasses to do special things when
     * the Apply button has been pressed.
     * The default implementation of this framework method simply calls
     * <code>performOk</code> to simulate the pressing of the page's OK button.
     * </p>
     *
     * @see #performOk()
     * @see #performCancel()
     */
    protected void performApply() {
        performOk();
    }

    /**
     * The preference page implementation of an <code>IPreferencePage</code>
     * method performs special processing when this page's Cancel button has
     * been pressed.
     * <p>
     * This is a framework hook method for subclasses to do special things when
     * the Cancel button has been pressed. The default implementation of this
     * framework method does nothing and returns <code>true</code>.
     * </p>
     * <p>
     * Note that UI guidelines on different platforms disagree on whether Cancel
     * should revert changes that have been applied with the Apply button.
     * wants applied changes to persist on Cancel, whereas
     * consider Apply a preview that should not be saved on Cancel. Eclipse applications
     * typically adhere to the Windows guidelines and just override {@link #performOk()} and save preferences there.
     * </p>
     * @see IPreferencePage#performCancel()
     */
    @Override
