	private class HandlerMock implements IUriSchemeHandler {

		public Set<String> handled = new HashSet<>();

		public boolean called = false;

		@Override
		public void handle(String uri) {
			called = true;
			handled.add(uri);
		}
	};

