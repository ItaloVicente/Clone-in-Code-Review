    /**
     * This is a property set on a user job if the user has not decided to
     * run the job in the background.
     * The value is set to <code>true</code> when the job starts and set to
     * <code>false</code> if the user subsequently decides to complete the job in the
     * background.
     * <p>
     * This property is not intended to be set by clients.
     * </p>
     * @see org.eclipse.core.runtime.jobs.Job#isUser()
     */
    public static final QualifiedName PROPERTY_IN_DIALOG = new QualifiedName(
            IProgressConstants.PROPERTY_PREFIX, "inDialog"); //$NON-NLS-1$
