	private static List<DiffEntry> compactSrcList(List<DiffEntry> in) {
		ArrayList<DiffEntry> r = new ArrayList<>(in.size());
		for (DiffEntry e : in) {
			if (e.changeType == ChangeType.DELETE)
				r.add(e);
		}
		return r;
	}

