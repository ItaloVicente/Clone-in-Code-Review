        return parent;
    }

    /**
     * Return the amount of progress we have had as a percentage. If there is no
     * progress or it is indeterminate return IProgressMonitor.UNKNOWN.
     *
     * @return int
     */
    int getPercentDone() {
    	TaskInfo info = getTaskInfo();
        if (info != null){
        	if(info.totalWork == IProgressMonitor.UNKNOWN) {
