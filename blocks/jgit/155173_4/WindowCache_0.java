			LongAdder la = openByteCountPerRepository.computeIfAbsent(
					repositoryId(pack)
			la.add(count);
		}

		private static String repositoryId(PackFile pack) {
			return pack.getPackFile().getParentFile().getParentFile()
					.getParent();
