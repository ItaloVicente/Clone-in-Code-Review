            setSelectionResult(variableNames);
        } else {
            setSelectionResult(null);
        }
        super.okPressed();
    }

    /**
     * Sets the dialog result to the concatenated variable name and extension.
     *
     * @param variable variable selected in the variables list and extended
     * 	by <code>extensionFile</code>
     * @param extensionFile file selected to extend the variable.
     */
    private void setExtensionResult(
            PathVariablesGroup.PathVariableElement variable, IFileStore extensionFile) {
        IPath extensionPath = new Path(extensionFile.toString());
