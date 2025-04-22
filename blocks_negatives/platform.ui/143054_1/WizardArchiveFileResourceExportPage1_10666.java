        String selectedFileName = dialog.open();

        if (selectedFileName != null) {
            setErrorMessage(null);
            setDestinationValue(selectedFileName);
        }
    }

    /**
     *	Hook method for saving widget values for restoration by the next instance
     *	of this class.
     */
    @Override
