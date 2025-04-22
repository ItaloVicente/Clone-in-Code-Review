			this.reuse = reuse;
		}

		public Iterator<RevCommit> iterator() {
			return new Iterator<RevCommit>() {
				int pos = commitStartPos;

				public boolean hasNext() {
					return pos < commitsByOldest.length;
				}

				public RevCommit next() {
					return commitsByOldest[pos++];
				}

				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
