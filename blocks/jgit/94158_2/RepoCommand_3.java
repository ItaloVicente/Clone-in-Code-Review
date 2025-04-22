	static URI relativize(URI current
		if (!target.toString().equals(target.getPath())) { return target; }
		if (!current.toString().equals(current.getPath())) { return target; }

		String cur = current.normalize().getPath();
		String dest = target.normalize().getPath();

		if (cur.startsWith("/") != dest.startsWith("/")) { return target; }

		while (cur.startsWith("/")) { cur = cur.substring(1); }
		while (dest.startsWith("/")) { dest = dest.substring(1); }

		if (!cur.endsWith("/")) {
			cur = cur.substring(0
		}
		String destFile = "";
		if (!dest.endsWith("/")) {
			destFile = dest.substring(dest.lastIndexOf('/')+1
			dest = dest.substring(0
		}

		String []cs = cur.split("/");
		String []ds = dest.split("/");

		int common = 0;
		while (common < cs.length && common < ds.length && cs[common].equals(ds[common])) {
			common++;
		}

		StringJoiner j = new StringJoiner("/");
		for (int i = common; i < cs.length; i++) {
			j.add("..");
		}
		for (int i = common; i < ds.length; i++) {
			j.add(ds[i]);
		}

		j.add(destFile);
		return URI.create(j.toString());
	}

