
	public static enum HunkControlChar {
		ADD('+')
		REMOVE('-')
		CONTEXT(' ')
		ENDLINE('\\');

		private final char character;

		HunkControlChar(char character) {
			this.character = character;
		}

		public char character() {
			return character;
		}

		public static HunkControlChar valueOf(char character) {
			for (HunkControlChar e : values())
				if (e.character() == character)
					return e;
			throw new IllegalArgumentException("Illegal character: "
					+ character);
		}
	}

