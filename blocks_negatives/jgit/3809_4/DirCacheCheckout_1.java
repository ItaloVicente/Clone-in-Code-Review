		builder.finish();

		File file=null;
		String last = "";
		for (int i = removed.size() - 1; i >= 0; i--) {
			String r = removed.get(i);
			file = new File(repo.getWorkTree(), r);
			if (!file.delete() && file.exists())
					toBeDeleted.add(r);
			else {
				if (!isSamePrefix(r, last))
					removeEmptyParents(new File(repo.getWorkTree(), last));
				last = r;
