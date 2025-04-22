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

	protected String getOutputSuffix() {
		if(zipFormatButton.getSelection()) {
			return ".zip"; //$NON-NLS-1$
		} else if(compressContentsCheckbox.getSelection()) {
			return ".tar.gz"; //$NON-NLS-1$
		} else {
			return ".tar"; //$NON-NLS-1$
		}
	}

	@Override
