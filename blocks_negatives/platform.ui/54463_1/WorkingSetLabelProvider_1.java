    @Override
	public Image getImage(Object object) {
        Assert.isTrue(object instanceof IWorkingSet);
        IWorkingSet workingSet = (IWorkingSet) object;
        ImageDescriptor imageDescriptor = workingSet.getImageDescriptor();

        if (imageDescriptor == null) {
