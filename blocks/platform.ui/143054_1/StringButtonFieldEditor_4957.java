			changeButton.addDisposeListener(event -> changeButton = null);
		} else {
			checkParent(changeButton, parent);
		}
		return changeButton;
	}

	@Override
