    private String oldFilePath;
    private String newFilePath;
    private String changeType;
    private int linesAdded;
    private int linesDeleted;
    private String diffText;

    public TextualDiff(final String oldFilePath,
                       final String newFilePath,
                       final String changeType,
                       final int linesAdded,
                       final int linesDeleted,
                       final String diffText) {
        this.oldFilePath = checkNotEmpty("oldFilePath",
                                         oldFilePath);
        this.newFilePath = checkNotEmpty("newFilePath",
                                         newFilePath);
        this.changeType = checkNotEmpty("changeType",
                                        changeType);

        this.linesAdded = linesAdded;
        this.linesDeleted = linesDeleted;

        this.diffText = checkNotEmpty("diffText",
                                      diffText);
    }

    public String getOldFilePath() {
        return oldFilePath;
    }

    public String getNewFilePath() {
        return newFilePath;
    }

    public String getChangeType() {
        return changeType;
    }

    public int getLinesAdded() {
        return linesAdded;
    }

    public int getLinesDeleted() {
        return linesDeleted;
    }

    public String getDiffText() {
        return diffText;
    }
