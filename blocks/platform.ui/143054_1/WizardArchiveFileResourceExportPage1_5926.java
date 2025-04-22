		String selectedFileName = dialog.open();

		if (selectedFileName != null) {
			setErrorMessage(null);
			setDestinationValue(selectedFileName);
		}
	}

	@Override
