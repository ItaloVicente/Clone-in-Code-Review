	protected abstract boolean accept(LfsRequest request

	protected static class LfsRequest {
		private String operation;

		private List<LfsObject> objects;

		public String getOperation() {
			return operation;
		}
	}

