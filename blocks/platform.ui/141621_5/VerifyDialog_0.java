		for (IDialogTestPass _dialogTest : _dialogTests) {
			int size = _dialogTest.checkListTexts().size();
			if (size > checkListSize) {
				checkListSize = size;
			}
		}
