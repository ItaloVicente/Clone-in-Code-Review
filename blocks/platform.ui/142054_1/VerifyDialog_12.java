				e.doit = false;
			}

		});
		_testDialog.open();
	}

	private void handleFailure() {
		IDialogTestPass test = _dialogTests[TEST_TYPE];
		StringBuilder text = new StringBuilder();
		String label = test.label();
