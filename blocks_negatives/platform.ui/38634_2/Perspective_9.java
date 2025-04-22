    }
    


    /**
     * Returns the old part reference.
     * Returns null if there was no previously active part.
     * 
     * @return the old part reference or <code>null</code>
     */
    public IWorkbenchPartReference getOldPartRef() {
        return oldPartRef;
    }

    /**
     * Sets the old part reference.
     * 
     * @param oldPartRef The old part reference to set, or <code>null</code>
     */
    public void setOldPartRef(IWorkbenchPartReference oldPartRef) {
        this.oldPartRef = oldPartRef;
    }

    protected void addActionSet(IActionSetDescriptor newDesc) {
    	IContextService service = page.getWorkbenchWindow().getService(IContextService.class);
    	try {
