    /**
     * Creates a set of radio menu items on the given menu.
     *
     * @param parent menu that will contain the menu items
     * @param newData the model that will store the value of the currently selected item
     */
    public RadioMenu(Menu parent, Model newData) {
        this.parent = parent;
        this.data = newData;

        newData.addChangeListener(this);
    }

    /**
     * Returns true iff the given values are considered equal.
     *
     * @param value1
     * @param value2
     * @return
     */
    private static boolean isEqual(Object value1, Object value2) {
        if (value1 == null) {
            return value2 == null;
        } else if (value2 == null) {
            return false;
        }

        return value1.equals(value2);
    }

    /**
     * Creates a new menu item with the given text and value. When
     * the item is selected, the state of the model will change to
     * match the given value.
     *
     * @param text
     * @param value
     */
    public void addMenuItem(String text, Object value) {
        MenuItem newItem = new MenuItem(parent, SWT.RADIO);

        newItem.setSelection(isEqual(data.getState(), value));
        newItem.setText(text);
        newItem.setData(value);
        items.add(newItem);

        newItem.addSelectionListener(selectionAdapter);
    }

    /**
     * Disposes all menu items
     */
    public void dispose() {
        Iterator iter = items.iterator();
        while (iter.hasNext()) {
            MenuItem next = (MenuItem) iter.next();

            if (!next.isDisposed()) {
                next.removeSelectionListener(selectionAdapter);
                next.dispose();
            }
        }

        items.clear();
    }

    /**
     * Refreshes the selected menu items to match the current state of the model.
     */
    private void refreshSelection() {
        Iterator iter = items.iterator();
        while (iter.hasNext()) {
            MenuItem next = (MenuItem) iter.next();

            if (!next.isDisposed()) {
                next.setSelection(isEqual(data.getState(), next.getData()));
            }
        }
    }

    @Override
