            details = detailsText;
            detailsArea.layout(true);
        } else {
            detailsButton.setText(IDialogConstants.SHOW_DETAILS_LABEL);
        }
    }


    private String getDetails(IStatus status) {
        if (status.getException() != null) {
            return getStackTrace(status.getException());
        }

    }

    private String getStackTrace(Throwable throwable) {
        StringWriter swriter = new StringWriter();
        PrintWriter pwriter = new PrintWriter(swriter);
        throwable.printStackTrace(pwriter);
        pwriter.flush();
        pwriter.close();
        return swriter.toString();
    }

    private void createShowLogButton(Composite parent){
