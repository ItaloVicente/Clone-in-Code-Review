				try {
					final RevCommit r = next;
					next = RevWalk.this.next();
					return r;
				} catch (IOException e) {
					throw new RevWalkException(e);
				}
