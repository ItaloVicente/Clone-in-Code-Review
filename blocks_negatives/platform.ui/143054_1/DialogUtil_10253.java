        if (nestedException != null) {
            ErrorDialog.openError(parent, title, message, nestedException
                    .getStatus());
        } else {
            MessageDialog.openError(parent, title, message);
        }
    }
