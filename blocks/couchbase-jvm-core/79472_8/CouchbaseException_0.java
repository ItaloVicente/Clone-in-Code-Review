    @InterfaceAudience.Public
    @InterfaceStability.Experimental
    public ResponseStatusDetails details() {
        return responseStatusDetails;
    }

    @InterfaceAudience.Private
    @InterfaceStability.Experimental
    public void details(final ResponseStatusDetails responseStatusDetails) {
        this.responseStatusDetails = responseStatusDetails;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();

        if (responseStatusDetails != null) {
            message +=  " (Context: " + responseStatusDetails.context() + ", Reference: "
                    + responseStatusDetails.reference() + ")";
        }
        return message;
    }
