	protected static class ChangeAndPatchSet {
		private final String changeNumber;

		private final String patchSetNumber;

		protected ChangeAndPatchSet(String change, String patchSet) {
			changeNumber = change;
			patchSetNumber = patchSet;
		}

		protected String getChangeNumber() {
			return changeNumber;
		}

		protected String getPatchSetNumber() {
			return patchSetNumber;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof ChangeAndPatchSet)) {
				return false;
			}
			ChangeAndPatchSet other = (ChangeAndPatchSet) obj;
			return Objects.equals(changeNumber, other.changeNumber)
					&& Objects.equals(patchSetNumber, other.patchSetNumber);
		}

		@Override
		public int hashCode() {
			return Objects.hash(changeNumber, patchSetNumber);
		}

		@Override
		public String toString() {
			StringBuilder result = new StringBuilder();
			result.append('<').append(changeNumber);
			if (patchSetNumber != null) {
				result.append(',').append(patchSetNumber);
			}
			return result.append('>').toString();
		}
	}

