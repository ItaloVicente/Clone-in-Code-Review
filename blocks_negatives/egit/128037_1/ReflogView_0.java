
			private RevCommit getCommit(final ReflogEntry entry) {
				try (RevWalk walk = new RevWalk(getRepository())) {
					walk.setRetainBody(true);
					return walk.parseCommit(entry.getNewId());
				} catch (IOException ignored) {
					return null;
				}
			}
