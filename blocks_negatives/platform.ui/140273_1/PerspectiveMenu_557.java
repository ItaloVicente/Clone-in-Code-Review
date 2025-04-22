        if (action == null) {
            IPerspectiveDescriptor descriptor = reg.findPerspectiveWithId(id);
            if (descriptor != null) {
                action = new OpenPerspectiveAction(window, descriptor, this);
                action.setActionDefinitionId(id);
                actions.put(id, action);
            }
        }
        return action;
    }

    /*
     * Returns the perspective shortcut items for the active perspective.
     *
     * @return a list of <code>IPerspectiveDescriptor</code> items
     */
