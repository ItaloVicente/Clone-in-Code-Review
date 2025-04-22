	private static class RefToRevCommitTranslator
			implements Function<Ref

		private RevWalk walk;

		RefToRevCommitTranslator(RevWalk walk) {
			this.walk = walk;
		}

		@Override
		public RevCommit apply(Ref ref) {
			ObjectId objectId = firstNonNull(ref.getPeeledObjectId()
			try {
				return walk.parseCommit(objectId);
			} catch (IncorrectObjectTypeException | MissingObjectException e) {
				return null;
			} catch (IOException e) {
				return null;
			}
		}

		private static ObjectId firstNonNull(ObjectId one
			return one != null ? one : two;
		}
	}

