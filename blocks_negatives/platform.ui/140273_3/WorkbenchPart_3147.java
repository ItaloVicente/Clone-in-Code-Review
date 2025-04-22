        return partName;
    }

    /**
     * Sets the name of this part. The name will be shown in the tab area for
     * the part. Clients should call this method instead of overriding getPartName.
     * Setting this to the empty string will cause a default part name to be used.
     *
     * <p>
     * setPartName and setContentDescription are intended to replace setTitle.
     * This may change a value that was previously set using setTitle.
     * </p>
     *
     * @param partName the part name, as it should be displayed in tabs.
     *
     * @since 3.0
     */
    protected void setPartName(String partName) {

        internalSetPartName(partName);

        setDefaultTitle();
    }

    void setDefaultTitle() {
        String description = getContentDescription();
        String name = getPartName();
        String newTitle = name;

        if (!Util.equals(description, "")) { //$NON-NLS-1$
            newTitle = MessageFormat
                    .format(
                            WorkbenchMessages.WorkbenchPart_AutoTitleFormat, name, description);
        }

        setTitle(newTitle);
    }

    /**
     * {@inheritDoc}
     * <p>
     * It is considered bad practise to overload or extend this method.
     * Parts should call setContentDescription to change their content description.
     * </p>
     */
    @Override
