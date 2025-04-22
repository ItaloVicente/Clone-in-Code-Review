	private final Repository repo;

	private static final class HashCount {
		int hash;

		int count;

		HashCount next;

		HashCount(int hash
			this.hash = hash;
			this.count = count;
			this.next = next;
		}
	}

	public RenameDetector(Repository repo) {
		this.repo = repo;
	}

