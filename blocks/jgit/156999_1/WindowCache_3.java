		public void recordOpenBytes(PackFile pack
			openByteCount.add(delta);
			String repositoryId = repositoryId(pack);
			LongAdder la = openByteCountPerRepository
					.computeIfAbsent(repositoryId
			la.add(delta);
			if (delta < 0) {
				openByteCountPerRepository.computeIfPresent(repositoryId
						(k
			}
		}

		private static String repositoryId(PackFile pack) {
			return pack.getPackFile().getParentFile().getParentFile()
					.getParent();
