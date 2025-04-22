
	static class TestFactory extends AbstractItemFactory<TestFactory, TableColumn, Table> {

		protected TestFactory(int style) {
			super(TestFactory.class, parent -> new TableColumn(parent, style));
		}

		public static TestFactory newTest() {
			return new TestFactory(SWT.NONE);
		}
	}
}
