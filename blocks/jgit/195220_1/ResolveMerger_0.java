		EolStreamType checkoutStreamType = workTreeUpdater
				.detectCheckoutStreamType(attributes[T_THEIRS]);
		String checkoutSmudgeCommand = tw
				.getSmudgeCommand(attributes[T_THEIRS]);
		workTreeUpdater.addToCheckout(path
				cleanupSmudgeCommand
				checkoutSmudgeCommand);
