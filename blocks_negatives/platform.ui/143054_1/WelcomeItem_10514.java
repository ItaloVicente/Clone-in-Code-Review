    private String text;

    private int[][] boldRanges;

    private int[][] helpRanges;

    private String[] helpIds;

    private String[] helpHrefs;

    private int[][] actionRanges;

    private String[] actionPluginIds;

    private String[] actionClasses;

    /**
     * Creates a new welcome item
     */
    public WelcomeItem(String text, int[][] boldRanges, int[][] actionRanges,
            String[] actionPluginIds, String[] actionClasses,
            int[][] helpRanges, String[] helpIds, String[] helpHrefs) {

        this.text = text;
        this.boldRanges = boldRanges;
        this.actionRanges = actionRanges;
        this.actionPluginIds = actionPluginIds;
        this.actionClasses = actionClasses;
        this.helpRanges = helpRanges;
        this.helpIds = helpIds;
        this.helpHrefs = helpHrefs;
    }

    /**
     * Returns the action ranges (character locations)
     */
    public int[][] getActionRanges() {
        return actionRanges;
    }

    /**
     * Returns the bold ranges (character locations)
     */
    public int[][] getBoldRanges() {
        return boldRanges;
    }

    /**
     * Returns the help ranges (character locations)
     */
    public int[][] getHelpRanges() {
        return helpRanges;
    }

    /**
     * Returns the text to display
     */
    public String getText() {
        return text;
    }

    /**
     * Returns true is a link (action or help) is present at the given character location
     */
    public boolean isLinkAt(int offset) {
        for (int[] helpRange : helpRanges) {
            if (offset >= helpRange[0]
                    && offset < helpRange[0] + helpRange[1]) {
                return true;
            }
        }

        for (int[] actionRange : actionRanges) {
            if (offset >= actionRange[0]
                    && offset < actionRange[0] + actionRange[1]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Logs a error to the workbench log
     */
    public void logActionLinkError(String actionPluginId, String actionClass) {
        IDEWorkbenchPlugin
    }

    /**
     * Open a help topic
     */
    private void openHelpTopic(String topic, String href) {
        if (href != null) {
