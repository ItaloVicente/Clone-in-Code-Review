                editButton.setEnabled(enabled);
                removeButton.setEnabled(enabled);
            }
        }
    }

    /**
     * Updates the widget's current state: refreshes the table with the current
     * defined variables, selects the item corresponding to the given variable
     * (selects the first item if <code>null</code> is provided) and updates
     * the enabled state for the Add/Remove/Edit buttons.
     *
     */
    private void updateWidgetState() {
    	variableTable.refresh();
        updateEnabledState();
    }

    /**
     * @param resource
     */
    public void setResource(IResource resource) {
    	currentResource = resource;
        if (resource != null)
        	pathVariableManager = resource.getPathVariableManager();
        else
        	pathVariableManager = ResourcesPlugin.getWorkspace().getPathVariableManager();
