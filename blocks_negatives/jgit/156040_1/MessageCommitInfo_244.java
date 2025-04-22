    public static final String MERGE_MESSAGE = "Merge branch '%s'";
    public static final String REVERT_MERGE_MESSAGE = "Revert merge from branch '%s'";
    public static final String FIX_REVERT_MERGE_MESSAGE = "Fix after merge reversion";

    public MessageCommitInfo(final String message) {
        this(null,
             null,
             null,
             message,
             null,
             null);
    }

    private MessageCommitInfo(final String sessionId,
                              final String name,
                              final String email,
                              final String message,
                              final TimeZone timeZone,
                              final Date when) {
        super(sessionId,
              name,
              email,
              message,
              timeZone,
              when);
    }

    public static MessageCommitInfo createMergeMessage(final String sourceBranch) {
        return new MessageCommitInfo(String.format(MERGE_MESSAGE, sourceBranch));
    }

    public static MessageCommitInfo createRevertMergeMessage(final String sourceBranch) {
        return new MessageCommitInfo(String.format(REVERT_MERGE_MESSAGE, sourceBranch));
    }

    public static MessageCommitInfo createFixMergeReversionMessage() {
        return new MessageCommitInfo(FIX_REVERT_MERGE_MESSAGE);
    }
