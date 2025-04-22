
	public static Set<String> getRefsAndDirs(Set<String> refNames) {
		Set<String> refsAndDirs = new HashSet<>();
		refNames.stream().forEach(ref -> {
			List<String> dirLvls = Arrays.asList(ref.split("/"));
			if (dirLvls.size() <= 1) {
				return;
			}
			refsAndDirs.add(ref);
			String parentDirBuf = ref;
			int lvlRemovalPt = dirLvls.size() - 1;
			while (lvlRemovalPt > 1) {
				int subtractLen = dirLvls.get(lvlRemovalPt).length() + 1;
				parentDirBuf = parentDirBuf.substring(0
				if (refsAndDirs.contains(parentDirBuf)) {
					return;
				}
				refsAndDirs.add(parentDirBuf);
				lvlRemovalPt--;
			}
		});
		return refsAndDirs;
	}
