		return suite.getDataForParameterizedRunner();
	}

	public static class RunnerFactory implements ParametersRunnerFactory {
		@Override
		public Runner createRunnerForTestWithParameters(TestWithParameters test) throws InitializationError {
			Class<?> testClass = (Class<?>) test.getParameters().get(0);
			List<Object> parameters = test.getParameters().subList(1, test.getParameters().size());
			String testName = testClass.getSimpleName() + " for " + parameters.get(0);
			return new BlockJUnit4ClassRunnerWithParameters(
					new TestWithParameters(testName, new TestClass(testClass), parameters)) {
				@Override
				protected Annotation[] getRunnerAnnotations() {
					return new Annotation[0];
				}
			};
		}
