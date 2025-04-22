	public static final IMatcher NO_MATCH = new IMatcher() {
		public boolean matches(String path
			return false;
		}

		public boolean matches(String segment
				boolean assumeDirectory) {
			return false;
		}
	};

