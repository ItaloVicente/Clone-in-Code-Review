    }

    /**
     * Answers whether the definition is currently set to the default value.
     *
     * @param definition the <code>ColorDefinition</code> to check.
     * @return Return whether the definition is currently mapped to the default
     * 		value, either in the preference store or in the local change record
     * 		of this preference page.
     */
    private boolean isDefault(ColorDefinition definition) {
        String id = definition.getId();
