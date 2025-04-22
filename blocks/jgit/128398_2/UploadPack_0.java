	private final class ShallowSplit {
		private final List<ObjectId> shallowCommits = new ArrayList<>();

		private final List<ObjectId> unshallowCommits = new ArrayList<>();

		void addShallow(ObjectId shallow) {
			getShallowCommits().add(shallow);
		}

		void addUnshallow(ObjectId unshallow) {
			getUnshallowCommits().add(unshallow);
		}

		List<ObjectId> getShallowCommits() {
			return shallowCommits;
		}

		List<ObjectId> getUnshallowCommits() {
			return unshallowCommits;
		}

		void write(PacketLineOut out) throws IOException {
			for (ObjectId o : shallowCommits) {
			}

			for (ObjectId u : unshallowCommits) {
			}
		}
	}

