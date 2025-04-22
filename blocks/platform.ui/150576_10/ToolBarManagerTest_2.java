	private final class ObservableControlContribution extends ControlContribution {
		private boolean updateCalled;
		private boolean computeWidthCalled;

		private ObservableControlContribution(String id) {
			super(id);
		}

		@Override
		protected Control createControl(Composite parent) {
			return new ComboViewer(parent).getControl();
		}

		@Override
		public void update() {
			super.update();
			updateCalled = true;
		}

		@Override
		protected int computeWidth(Control control) {
			int computeWidth = super.computeWidth(control);
			computeWidthCalled = true;
			return computeWidth;
		}

	}

