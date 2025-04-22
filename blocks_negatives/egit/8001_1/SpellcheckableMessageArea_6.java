
			offset += lineDelimiterLength;
		}

		return wrapEdits;
	}

	/**
	 * Edit for replacing a text range with another text to wrap or join lines.
	 */
	public static class WrapEdit {
		private final int start;
		private final int length;
		private final String replacement;

		/**
		 * @param start see {@link #getStart()}
		 * @param length see {@link #getLength()}
		 * @param replacement see {@link #getReplacement()}
		 */
		public WrapEdit(int start, int length, String replacement) {
			this.start = start;
			this.length = length;
			this.replacement = replacement;
		}

		/**
		 * @return character offset of where the edit should be applied on the
		 *         text
		 */
		public int getStart() {
			return start;
		}

		/**
		 * @return number of characters which should be replaced by the
		 *         replacement text
		 */
		public int getLength() {
			return length;
