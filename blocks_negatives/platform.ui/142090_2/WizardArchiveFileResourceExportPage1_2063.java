            } else {
                displayErrorDialog(DataTransferMessages.ZipExport_alreadyExistsError);
                giveFocusToDestination();
                return false;
            }
        }

        return true;
    }

    /**
     * Ensures that the target output file and its containing directory are
     * both valid and able to be used.  Answer a boolean indicating validity.
     */
    protected boolean ensureTargetIsValid() {
        String targetPath = getDestinationValue();

        if (!ensureTargetDirectoryIsValid(targetPath)) {
