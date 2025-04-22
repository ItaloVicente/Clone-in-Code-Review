            if (activeTextControl != null && !activeTextControl.isDisposed()) {
                activeTextControl.selectAll();
                updateActionsEnableState();
                return;
            }
            if (selectAllAction != null) {
                selectAllAction.runWithEvent(event);
                return;
            }
        }

        /**
         * Update the state.
         */
        public void updateEnabledState() {
            if (activeTextControl != null && !activeTextControl.isDisposed()) {
                setEnabled(activeTextControl.getCharCount() > 0);
                return;
            }
            if (selectAllAction != null) {
                setEnabled(selectAllAction.isEnabled());
                return;
            }
            setEnabled(false);
        }
    }

    /**
     * Creates a <code>Text</code> control action handler
     * for the global Cut, Copy, Paste, Delete, and Select All
     * of the action bar.
     *
     * @param actionBar the action bar to register global
     *    action handlers for Cut, Copy, Paste, Delete,
     * 	  and Select All
     */
    public TextActionHandler(IActionBars actionBar) {
        super();
        actionBars = actionBar;
        updateActionBars();
    }

    /**
     * Updates the actions bars.
     *
