		final TreeWalk walk = new TreeWalk(db);
		final ObjectReader reader = walk.getObjectReader();
		final MutableObjectId idBuf = new MutableObjectId();
		final Archiver fmt = formats.get(format);
		final ArchiveOutputStream outa = fmt.createArchiveOutputStream(outs);

