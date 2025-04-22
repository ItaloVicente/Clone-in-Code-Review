    /**
     * This property is used to associate an <code>ImageDescriptor</code> with a Job.
     * If the Job is shown in the UI, this descriptor is used to create an icon that
     * represents the Job.
     * <p>
     * Please note, that this property is only used if no <code>ImageDescriptor</code> has been
     * registered for the Job family with the <code>IProgressService</code>.
     * </p>
     * @see org.eclipse.jface.resource.ImageDescriptor
     * @see org.eclipse.e4.ui.progress.IProgressService
     **/
    public static final QualifiedName ICON_PROPERTY = new QualifiedName(
            PROPERTY_PREFIX, "icon"); //$NON-NLS-1$
