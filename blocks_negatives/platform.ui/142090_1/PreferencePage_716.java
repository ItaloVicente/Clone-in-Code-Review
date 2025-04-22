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
