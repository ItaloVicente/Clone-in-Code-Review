    /**
     * Allocates a new DrillFrame.
     *
     * @param oElement the tree input element
     * @param strPropertyName the visible tree property
     * @param vExpansion the current expansion state of the tree
     */
    public DrillFrame(Object oElement, Object strPropertyName, List vExpansion) {
        fElement = oElement;
        fPropertyName = strPropertyName;
        fExpansion = vExpansion;
    }
