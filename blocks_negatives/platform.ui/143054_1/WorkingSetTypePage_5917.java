        super.dispose();
    }

    /**
     * Returns the page id of the selected working set type.
     *
     * @return the page id of the selected working set type.
     */
    public String getSelection() {
        WorkingSetDescriptor descriptor = getSelectedWorkingSet();
        if (descriptor != null)
        	return descriptor.getId();

        return null;
    }
