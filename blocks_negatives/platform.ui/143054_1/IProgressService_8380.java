    /**
     * Get the icon that has been registered for a Job by
     * checking if the job belongs to any of the registered
     * families.
     * @param job
     * @return Icon or <code>null</code> if there isn't one.
     * @see IProgressService#registerIconForFamily(ImageDescriptor,Object)
     */
    public Image getIconFor(Job job);
