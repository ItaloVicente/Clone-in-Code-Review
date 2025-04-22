        int size = shortcuts.size();
        for (int i = 0; i < size; i++) {
            if (!list.contains(shortcuts.get(i))) {
                list.add(shortcuts.get(i));
            }
        }

        return list;
    }

    /**
     * Returns whether the menu item representing the active perspective
     * will have a check mark.
     *
     * @return <code>true</code> if a check mark is shown, <code>false</code> otherwise
     */
    protected boolean getShowActive() {
        return showActive;
    }

    /**
     * Returns the window for this menu.
     *
     * @return the window
     */
    protected IWorkbenchWindow getWindow() {
        return window;
    }

    @Override
