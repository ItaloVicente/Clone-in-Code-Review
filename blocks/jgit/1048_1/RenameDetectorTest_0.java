	private static class RenameDetectorForTesting extends RenameDetector {
		public RenameDetectorForTesting(Repository repo) {
			super(repo);
		}

		@Override
		int hashLine(byte[] raw
			if (ptr == end)
				return 0;
			if (end - ptr == 1)
				return raw[ptr];

			return (raw[ptr] + raw[ptr + 1] * HASH_TABLE_SIZE) * 2;
		}
	}

