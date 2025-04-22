	static URI relativize(URI current
		if (!target.toString().equals(target.getPath())) {
			return target;
		}
		if (!current.toString().equals(current.getPath())) {
			return target;
		}

		String cur = current.normalize().getPath();
		String dest = target.normalize().getPath();

			return target;
		}

			cur = cur.substring(1);
		}
			dest = dest.substring(1);
		}

			cur = cur.substring(0
		}
			destFile = dest.substring(dest.lastIndexOf('/') + 1
			dest = dest.substring(0
		}


		int common = 0;
		while (common < cs.length && common < ds.length && cs[common].equals(ds[common])) {
			common++;
		}

		for (int i = common; i < cs.length; i++) {
		}
		for (int i = common; i < ds.length; i++) {
			j.add(ds[i]);
		}

		j.add(destFile);
		return URI.create(j.toString());
	}

