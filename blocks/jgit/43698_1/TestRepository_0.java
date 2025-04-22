		public CommitBuilder ident(PersonIdent ident) {
			author = ident;
			committer = ident;
			return this;
		}

		public CommitBuilder author(PersonIdent a) {
			author = a;
			return this;
		}

		public CommitBuilder committer(PersonIdent c) {
			committer = c;
			return this;
		}

