			changeId = "";
			return this;
		}

		public CommitBuilder insertChangeId(String c) {
			ObjectId.fromString(c);
			changeId = c;
