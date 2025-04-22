    /**
     * Sets or clears the error message.
     * If not <code>null</code>, the OK button is disabled.
     *
     * @param errorMessage
     *            the error message, or <code>null</code> to clear
     * @since 3.0
     */
    public void setErrorMessage(String errorMessage) {
    	this.errorMessage = errorMessage;
    	if (errorMessageText != null && !errorMessageText.isDisposed()) {
    		boolean hasError = errorMessage != null && (StringConverter.removeWhiteSpaces(errorMessage)).length() > 0;
    		errorMessageText.setEnabled(hasError);
    		errorMessageText.setVisible(hasError);
    		errorMessageText.getParent().update();
    		Control button = getButton(IDialogConstants.OK_ID);
    		if (button != null) {
    			button.setEnabled(errorMessage == null);
    		}
    	}
    }
