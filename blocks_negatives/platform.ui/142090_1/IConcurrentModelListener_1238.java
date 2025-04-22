    /**
     * Notifies the receiver about the complete set
     * of elements in the model. Most models will
     * not call this method unless the listener explicitly
     * requests it by calling
     * <code>IConcurrentModel.requestUpdate</code>
     *
     * @param newContents contents of the model
     */
    public void setContents(Object[] newContents);
