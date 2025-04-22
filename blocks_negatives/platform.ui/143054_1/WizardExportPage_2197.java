            } else {
                selectAppropriateFolderContents((IContainer) resource);
            }
        }
    }

    /**
     * Sets the selection value of this page's "Export all types" radio, or stores
     * it for future use if this visual component does not exist yet.
     *
     * @param value new selection value
     */
    public void setExportAllTypesValue(boolean value) {
        if (exportAllTypesRadio == null) {
            initialExportAllTypesValue = value;
            exportAllResourcesPreSet = true;
        } else {
            exportAllTypesRadio.setSelection(value);
            exportSpecifiedTypesRadio.setSelection(!value);
        }
    }

    /**
     * Sets the value of this page's source resource field, or stores
     * it for future use if this visual component does not exist yet.
     *
     * @param value new value
     */
    public void setResourceFieldValue(String value) {
        if (resourceNameField == null) {
