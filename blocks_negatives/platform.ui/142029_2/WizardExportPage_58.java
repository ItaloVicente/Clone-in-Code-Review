        }

        return result;
    }

    /**
     * Returns the current selection value of the "Export all types" radio,
     * or its set initial value if it does not exist yet.
     *
     * @return the "Export All Types" radio's current value or anticipated initial
     *   value
     */
    public boolean getExportAllTypesValue() {
        if (exportAllTypesRadio == null) {
			return initialExportAllTypesValue;
