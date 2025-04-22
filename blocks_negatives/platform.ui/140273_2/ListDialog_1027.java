        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.heightHint = convertHeightInCharsToPixels(heightInChars);
        gd.widthHint = convertWidthInCharsToPixels(widthInChars);
        Table table = fTableViewer.getTable();
        table.setLayoutData(gd);
        table.setFont(container.getFont());
        return parent;
    }

    /**
     * Return the style flags for the table viewer.
     * @return int
     */
    protected int getTableStyle() {
        return SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER;
    }

    /*
     * Overrides method from Dialog
     */
    @Override
