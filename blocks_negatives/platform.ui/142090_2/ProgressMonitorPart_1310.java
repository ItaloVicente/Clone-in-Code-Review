    /**
     * Return the label for showing tasks
     * @return String
     */
    private String taskLabel() {
    	boolean hasTask= fTaskName != null && fTaskName.length() > 0;
    	boolean hasSubtask= fSubTaskName != null && fSubTaskName.length() > 0;
