				enable = false;
			}
		}
		_yesButton.setEnabled(enable);
	}

	private void initializeTest() {
		IDialogTestPass test = _dialogTests[TEST_TYPE];
		setTitle(test.title());
		setMessage(test.description());
