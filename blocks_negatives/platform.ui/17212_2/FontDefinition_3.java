        if (parsedValue == null) {
            parsedValue = JFaceResources.getFontRegistry().filterData(
                    StringConverter.asFontDataArray(value),
                    PlatformUI.getWorkbench().getDisplay());
        }

        return parsedValue;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.internal.themes.IEditable#isEditable()
     */
    public boolean isEditable() {
        return isEditable;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (obj instanceof FontDefinition) {
            return getId().equals(((FontDefinition)obj).getId());
        }
        return false;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return id.hashCode();
    }    
