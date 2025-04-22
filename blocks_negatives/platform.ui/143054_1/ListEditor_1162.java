        } else {
            checkParent(buttonBox, parent);
        }

        selectionChanged();
        return buttonBox;
    }

    /**
     * Returns this field editor's list control.
     *
     * @param parent the parent control
     * @return the list control
     */
    public List getListControl(Composite parent) {
        if (list == null) {
            list = new List(parent, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL
                    | SWT.H_SCROLL);
            list.setFont(parent.getFont());
            list.addSelectionListener(getSelectionListener());
            list.addDisposeListener(event -> list = null);
        } else {
            checkParent(list, parent);
        }
        return list;
    }

    /**
     * Creates and returns a new item for the list.
     * <p>
     * Subclasses must implement this method.
     * </p>
     *
     * @return a new item
     */
    protected abstract String getNewInputObject();

    @Override
