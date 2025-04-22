    /**
     * @param string Sets the text string in the receiver
     */
    public void setText(String string) {
    	String oldValue = text.getText();
    	if (editMaskParser != null) {
