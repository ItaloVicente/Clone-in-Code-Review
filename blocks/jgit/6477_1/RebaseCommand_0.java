
		@Override
		public String toString() {
			return "Step[" + action + "
					+ ((commit == null) ? "null" : commit)
					+ "
					+ ((shortMessage == null) ? "null" : new String(
							shortMessage)) + "]";
		}
