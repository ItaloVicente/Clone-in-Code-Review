	private static class StackTrace extends Throwable {
		private static final long serialVersionUID = -2829405667536819137L;

		@Override
		public String toString() {
			return Messages.DefaultUiFreezeEventLogger_stack_trace_header;
		}
	}

