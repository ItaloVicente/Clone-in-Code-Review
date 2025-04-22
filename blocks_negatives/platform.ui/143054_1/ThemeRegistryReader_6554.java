        String description = null;
        boolean isEditable = true;
        String isEditableString = element.getAttribute(IWorkbenchRegistryConstants.ATT_IS_EDITABLE);
        if (isEditableString != null) {
            isEditable = Boolean.valueOf(isEditableString).booleanValue();
        }
