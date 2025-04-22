		super.setErrorMessage(newMessage);
		if (isCurrentPage()) {
			getContainer().updateMessage();
		}
	}

	@Override
