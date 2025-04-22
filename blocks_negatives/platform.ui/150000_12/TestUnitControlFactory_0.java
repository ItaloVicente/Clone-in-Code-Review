
	static class TestFactory extends AbstractControlFactory<TestFactory, Label> {

		protected TestFactory(int style) {
			super(TestFactory.class, parent -> new Label(parent, style));
		}

		public static TestFactory newTest() {
			return new TestFactory(SWT.NONE);
		}
	}
