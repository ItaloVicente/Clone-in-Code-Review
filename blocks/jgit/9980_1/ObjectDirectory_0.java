		final List<PackExt> extensions = new ArrayList<PackExt>(
				PackExt.values().length);
		extensions.add(PACK);
		extensions.add(INDEX);

		final String base = p.substring(0
		for (PackExt ext : PackExt.values()) {
			if (ext != PACK && ext != INDEX) {
				final String name = base + ext.getExtension();
				if (new File(name).exists())
					extensions.add(ext);
			}
		}

		PackFile res = new PackFile(pack
