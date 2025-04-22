    /**
     * The KEEPONE_PROPERTY is an extension to the KEEP_PROPERTY, that provides a hint
     * to the progress UI to ensure that only a single Job of a Job family is kept in the
     * set of kept Jobs. That is, whenever a Job that has the KEEPONE_PROPERTY starts or finishes,
     * all other kept Jobs of the same family are removed first.
     * <p>
     * Membership to family is determined using a Job's <code>belongsTo</code>
     * method. The progress service will pass each job that currently exists in the
     * view to the <code>belongsTo</code> method of a newly added job. Clients who
     * set the <code>KEEPONE_PROPERTY</code> must implement a <code>belongsTo</code>
     * method that determines if the passed job is of the same family as their job
     * and return <code>true</code> if it is.
     * </p>
     * <p>
     * Please note that other Jobs of the same family are only removed if they have finished.
     * Non finished jobs of the same family are left alone.
     * </p>
     **/
    public static final QualifiedName KEEPONE_PROPERTY = new QualifiedName(
            PROPERTY_PREFIX, "keepone"); //$NON-NLS-1$
