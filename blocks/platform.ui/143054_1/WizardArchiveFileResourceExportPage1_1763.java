		String destinationValue = getDestinationValue();
		if (destinationValue.endsWith(".tar")) { //$NON-NLS-1$
			compressContentsCheckbox.setSelection(false);
			targzFormatButton.setSelection(true);
			zipFormatButton.setSelection(false);
		} else if (destinationValue.endsWith(".tar.gz") //$NON-NLS-1$
