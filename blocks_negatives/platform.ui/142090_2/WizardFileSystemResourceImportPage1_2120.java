        return result.toOSString();
    }

    /**
     *	Answer the string to display as the label for the source specification field
     */
    protected String getSourceLabel() {
        return DataTransferMessages.FileImport_fromDirectory;
    }

    /**
     *	Handle all events and enablements for widgets in this dialog
     *
     * @param event Event
     */
    @Override
