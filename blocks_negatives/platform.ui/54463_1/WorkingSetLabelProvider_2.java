    @Override
	public String getText(Object object) {
        Assert.isTrue(object instanceof IWorkingSet);
        IWorkingSet workingSet = (IWorkingSet) object;
        return workingSet.getLabel();
    }
