		final DirCache treeB = db.readDirCache();
		final DirCache treeO = db.readDirCache();
		final DirCache treeT = db.readDirCache();
		{
			final DirCacheBuilder b = treeB.builder();
			final DirCacheBuilder o = treeO.builder();
			final DirCacheBuilder t = treeT.builder();

			b.add(createEntry("d/o", FileMode.REGULAR_FILE));
			b.add(createEntry("d/t", FileMode.REGULAR_FILE));

			o.add(createEntry("d/o", FileMode.REGULAR_FILE));
			o.add(createEntry("d/t", FileMode.REGULAR_FILE, "o !"));

			t.add(createEntry("d/o", FileMode.REGULAR_FILE, "t !"));
			t.add(createEntry("d/t", FileMode.REGULAR_FILE, "t !"));

			b.finish();
			o.finish();
			t.finish();
		}
