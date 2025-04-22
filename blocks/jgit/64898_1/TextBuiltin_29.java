
	public static boolean containsHelp(String[] args) {
		for (String str : args) {
				return true;
			}
		}
		return false;
	}

	public static class TerminatedByHelpException extends Die {
		private static final long serialVersionUID = 1L;

		public TerminatedByHelpException() {
			super(true);
		}

	}
