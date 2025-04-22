		try {
			runCompare(event, repository);
		} catch (Exception e) {
			Activator.handleError(
					UIText.CompareWithRefAction_errorOnSynchronize, e, true);
		}
