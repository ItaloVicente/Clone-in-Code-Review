                removeSelectedVariables();
            }
        });
        removeButton.setFont(font);
        setButtonLayoutData(removeButton);
        updateEnabledState();
    }

    /**
     * Initializes the computation of horizontal and vertical dialog units
     * based on the size of current font.
     * <p>
     * This method must be called before <code>setButtonLayoutData</code>
     * is called.
     * </p>
     *
     * @param control a control from which to obtain the current font
     */
    protected void initializeDialogUnits(Control control) {
        GC gc = new GC(control);
        gc.setFont(control.getFont());
        fontMetrics = gc.getFontMetrics();
        gc.dispose();
    }

    /**
     * (Re-)Initialize collections used to mantain temporary variable state.
     */
    private void initTemporaryState() {
        tempPathVariables.clear();
