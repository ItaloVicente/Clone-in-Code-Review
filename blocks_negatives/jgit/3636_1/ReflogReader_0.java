	/**
	 * Parsed reflog entry
	 */
	static public class Entry {
		private ObjectId oldId;

		private ObjectId newId;

		private PersonIdent who;

		private String comment;

		Entry(byte[] raw, int pos) {
			oldId = ObjectId.fromString(raw, pos);
			pos += Constants.OBJECT_ID_STRING_LENGTH;
			if (raw[pos++] != ' ')
				throw new IllegalArgumentException(
						JGitText.get().rawLogMessageDoesNotParseAsLogEntry);
			newId = ObjectId.fromString(raw, pos);
			pos += Constants.OBJECT_ID_STRING_LENGTH;
			if (raw[pos++] != ' ') {
				throw new IllegalArgumentException(
						JGitText.get().rawLogMessageDoesNotParseAsLogEntry);
			}
			who = RawParseUtils.parsePersonIdentOnly(raw, pos);
			int p0 = RawParseUtils.next(raw, pos, '\t');
			if (p0 >= raw.length)
			else {
				int p1 = RawParseUtils.nextLF(raw, p0);
				comment = p1 > p0 ? RawParseUtils.decode(raw, p0, p1 - 1) : "";
			}
		}

		/**
		 * @return the commit id before the change
		 */
		public ObjectId getOldId() {
			return oldId;
		}

		/**
		 * @return the commit id after the change
		 */
		public ObjectId getNewId() {
			return newId;
		}

		/**
		 * @return user performin the change
		 */
		public PersonIdent getWho() {
			return who;
		}

		/**
		 * @return textual description of the change
		 */
		public String getComment() {
			return comment;
		}

		@Override
		public String toString() {
			return "Entry[" + oldId.name() + ", " + newId.name() + ", " + getWho() + ", "
					+ getComment() + "]";
		}
	}

