        return true;
    }

    /**
     * Performs special processing when this page's Defaults button has been pressed.
     * <p>
     * This is a framework hook method for subclasses to do special things when
     * the Defaults button has been pressed.
     * Subclasses may override, but should call <code>super.performDefaults</code>.
     * </p>
     */
    protected void performDefaults() {
        updateApplyButton();
    }


    @Override
