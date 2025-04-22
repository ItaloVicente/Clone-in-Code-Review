		super.setVisible(visible);
		if (visible) {
			getParentStatusLineManager().setErrorMessage(errorImage,
					errorMessage);
			getParentStatusLineManager().setMessage(messageImage, message);
		} else {
			getParentStatusLineManager().setMessage(null, null);
			getParentStatusLineManager().setErrorMessage(null, null);
		}
	}

	@Override
