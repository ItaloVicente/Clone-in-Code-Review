    /**
     * Creates a property sheet view.
     */
    public PropertySheet() {
        super();
        pinPropertySheetAction = new PinPropertySheetAction();
        RegistryFactory.getRegistry().addListener(this, EXT_POINT);
