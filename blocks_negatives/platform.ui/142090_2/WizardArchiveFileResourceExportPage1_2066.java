        String idealSuffix = getOutputSuffix();
        String destinationText = super.getDestinationValue();

        if (destinationText.length() != 0
                && !destinationText.endsWith(File.separator)) {
            int dotIndex = destinationText.lastIndexOf('.');
            if (dotIndex != -1) {
                int pathSepIndex = destinationText.lastIndexOf(File.separator);
                if (pathSepIndex != -1 && dotIndex < pathSepIndex) {
                    destinationText += idealSuffix;
                }
            } else {
                destinationText += idealSuffix;
            }
        }

        return destinationText;
    }

    /**
     *	Answer the suffix that files exported from this wizard should have.
     *	If this suffix is a file extension (which is typically the case)
     *	then it must include the leading period character.
     *
     */
    protected String getOutputSuffix() {
    	if(zipFormatButton.getSelection()) {
    	} else if(compressContentsCheckbox.getSelection()) {
    	} else {
    	}
    }

    /**
     *	Open an appropriate destination browser so that the user can specify a source
     *	to import from
     */
    @Override
