				RevObject obj;
				while ((obj = q.next()) != null) {
					if (!(obj instanceof RevCommit))
						throw new WantNotValidException(obj);
					walk.markStart((RevCommit) obj);
				}
			} catch (MissingObjectException notFound) {
				throw new WantNotValidException(notFound.getObjectId()
						notFound);
			} finally {
				q.release();
			}
			for (ObjectId id : reachableFrom) {
				try {
					walk.markUninteresting(walk.parseCommit(id));
				} catch (IncorrectObjectTypeException notCommit) {
					continue;
				}
