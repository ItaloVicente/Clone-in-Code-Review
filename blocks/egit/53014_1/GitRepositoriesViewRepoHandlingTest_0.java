		TestUtil.processUIEvents(500);
		int max = 5000;
		int slept = 0;
		while (ModalContext.getModalLevel() > 0 && slept < max) {
			TestUtil.processUIEvents(100);
			slept += 100;
		}

		shell.activate();
