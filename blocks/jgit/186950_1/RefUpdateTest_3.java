	protected static class TestData {

		Options options;

		TestData(Options options) {
			this.options = options;
		}

		@Override
		public String toString() {
			return options.useRefCache() ? "using Ref cache"
					: "without Ref cache";
		}
	}

	@Parameters(name = "{0}")
	public static TestData[] initTestData() {
		return new TestData[] { new TestData(new Options())
				new TestData(new Options().setUseRefCache(true)) };
	}

	@Parameter
	public TestData data;

	@Override
	protected Options getOptions() {
		return data.options;
	}

