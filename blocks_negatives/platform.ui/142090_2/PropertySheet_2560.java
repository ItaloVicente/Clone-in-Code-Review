    /**
     * No longer used but preserved to avoid api change
     */
    public static final String HELP_CONTEXT_PROPERTY_SHEET_VIEW = IPropertiesHelpContextIds.PROPERTY_SHEET_VIEW;

    /**
     * Extension point used to modify behavior of the view
     */

    /**
     * The initial selection when the property sheet opens
     */
    private ISelection bootstrapSelection;

    /**
     * The current selection of the property sheet
     */
    private ISelection currentSelection;

    /**
     * The current part for which this property sheets is active
     */
