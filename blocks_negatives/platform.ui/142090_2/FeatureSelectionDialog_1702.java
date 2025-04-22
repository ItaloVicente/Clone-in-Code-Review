    /**
     * List width in characters.
     */
    private static final int LIST_WIDTH = 60;

    /**
     * List height in characters.
     */
    private static final int LIST_HEIGHT = 10;

    /**
     * The feature about infos.
     */
    private AboutInfo[] features;

    /**
     * List to display the resolutions.
     */
    private ListViewer listViewer;

    /**
     * The help context id
     */
    private String helpContextId;

    /**
     * Creates an instance of this dialog to display
     * the given features.
     * <p>
     * There must be at least one feature.
     * </p>
     *
     * @param shell  the parent shell
     * @param features  the features to display
     * @param primaryFeatureId  the id of the primary feature or null if none
     * @param shellTitle  shell title
     * @param shellMessage  shell message
     * @param helpContextId  help context id
     */
    public FeatureSelectionDialog(Shell shell, AboutInfo[] features,
            String primaryFeatureId, String shellTitle, String shellMessage,
            String helpContextId) {

        super(shell);
        if (features == null || features.length == 0) {
            throw new IllegalArgumentException();
        }
        this.features = features;
        this.helpContextId = helpContextId;
        setTitle(shellTitle);
        setMessage(shellMessage);

