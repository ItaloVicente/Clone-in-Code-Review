        super.fillActionBars(flags);
        updateBuildActions(true);
        if ((flags & FILL_PROXY) == 0) {
            hookListeners();
        }
    }

    /**
     * Fills the coolbar with the workbench actions.
     */
    @Override
