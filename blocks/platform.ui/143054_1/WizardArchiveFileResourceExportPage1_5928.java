			compressContentsCheckbox.setSelection(true);
			targzFormatButton.setSelection(true);
			zipFormatButton.setSelection(false);
		} else if (destinationValue.endsWith(".zip")) { //$NON-NLS-1$
			zipFormatButton.setSelection(true);
			targzFormatButton.setSelection(false);
		}

		return super.validateDestinationGroup();
	}
