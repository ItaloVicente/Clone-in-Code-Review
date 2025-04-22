        }

        if (sampleStyledText == null) {
            this.scrolledComposite.getHorizontalBar().setIncrement(
                    HORZ_SCROLL_INCREMENT);
            this.scrolledComposite.getVerticalBar().setIncrement(
                    VERT_SCROLL_INCREMENT);
        } else {
            GC gc = new GC(sampleStyledText);
            int width = gc.getFontMetrics().getAverageCharWidth();
            gc.dispose();
            this.scrolledComposite.getHorizontalBar().setIncrement(width);
            this.scrolledComposite.getVerticalBar().setIncrement(
                    sampleStyledText.getLineHeight());
        }
        return infoArea;
    }

    /**
     * Creates the SWT controls for this workbench part.
     * <p>
     * Clients should not call this method (the workbench calls this method at
     * appropriate times).
     * </p>
     * <p>
     * For implementors this is a multi-step process:
     * <ol>
     *   <li>Create one or more controls within the parent.</li>
     *   <li>Set the parent layout as needed.</li>
     *   <li>Register any global actions with the <code>IActionService</code>.</li>
     *   <li>Register any popup menus with the <code>IActionService</code>.</li>
     *   <li>Register a selection provider with the <code>ISelectionService</code>
     *     (optional). </li>
     * </ol>
     * </p>
     *
     * @param parent the parent control
     */
    @Override
