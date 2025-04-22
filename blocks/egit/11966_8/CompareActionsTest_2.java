	private void clickCompareWithAndWaitForSync(String menuLabel) {
		JobJoiner jobJoiner = JobJoiner.startListening(
				ISynchronizeManager.FAMILY_SYNCHRONIZE_OPERATION, 60,
				TimeUnit.SECONDS);
		clickCompareWith(menuLabel);
		jobJoiner.join();
	}

