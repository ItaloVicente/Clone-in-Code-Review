			for (ObjectId id : reachableFrom) {
				try {
					walk.markUninteresting(walk.parseCommit(id));
				} catch (IncorrectObjectTypeException notCommit) {
					continue;
				}
