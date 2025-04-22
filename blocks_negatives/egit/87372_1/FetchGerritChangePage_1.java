	private final static class Change {
		private final String refName;

		private final Integer changeNumber;

		private final Integer patchSetNumber;

		static Change fromRef(String refName) {
			try {
					return null;
				if (tokens.length != 3)
					return null;
				Integer changeNumber = Integer.valueOf(tokens[1]);
				Integer patchSetNumber = Integer.valueOf(tokens[2]);
				return new Change(refName, changeNumber, patchSetNumber);
			} catch (NumberFormatException e) {
				return null;
			} catch (IndexOutOfBoundsException e) {
				return null;
			}
		}

		private Change(String refName, Integer changeNumber,
				Integer patchSetNumber) {
			this.refName = refName;
			this.changeNumber = changeNumber;
			this.patchSetNumber = patchSetNumber;
		}

		public String getRefName() {
			return refName;
		}

		public Integer getChangeNumber() {
			return changeNumber;
		}

		public Integer getPatchSetNumber() {
			return patchSetNumber;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return refName;
		}
	}

