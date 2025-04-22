
		@Override
		public boolean equals(Object o) {
			if (o == null || ! (o instanceof AlternateHandle))
				return false;
			AlternateHandle ah = (AlternateHandle) o;
			if (db == ah.db)
				return true;
			if (db == null)
				return false;
			return db.isMine(ah);
		}

		@Override
		public int hashCode() {
			if (db == null)
				return 1;
			Object altId = db.getAlternateId();
			if (altId == null)
				return 2;
			return altId.hashCode();
		}
