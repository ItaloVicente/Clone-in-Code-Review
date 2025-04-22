        StructuredSelection ssel = convertSelection(selection);
        if (!ssel.isEmpty()) {
            getViewer().getControl().setRedraw(false);
            getViewer().setSelection(ssel, true);
            getViewer().getControl().setRedraw(true);
        }
    }

    /**
     * Saves the filters defined as strings in <code>patterns</code>
     * in the preference store.
     */
    @Override
