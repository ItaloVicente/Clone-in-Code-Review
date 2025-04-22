		EolStreamType cleanupStreamType = workTreeUpdater.detectCheckoutStreamType(attributes[T_OURS]);
		String cleanupSmudgeCommand = tw.getSmudgeCommand(attributes[T_OURS]);
		EolStreamType checkoutStreamType = workTreeUpdater.detectCheckoutStreamType(attributes[T_THEIRS]);
		String checkoutSmudgeCommand = tw.getSmudgeCommand(attributes[T_THEIRS]);
		workTreeUpdater.addToCheckout(path
				checkoutStreamType
