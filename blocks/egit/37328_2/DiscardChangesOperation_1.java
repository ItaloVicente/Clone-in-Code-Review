	private Stage stage;

	public enum Stage {
		BASE(CheckoutCommand.Stage.BASE),
		OURS(CheckoutCommand.Stage.OURS),
		THEIRS(CheckoutCommand.Stage.THEIRS);

		private CheckoutCommand.Stage checkoutStage;

		private Stage(CheckoutCommand.Stage checkoutStage) {
			this.checkoutStage = checkoutStage;
		}
	}

