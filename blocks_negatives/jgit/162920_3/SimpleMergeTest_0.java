		final DirCache treeB = db.readDirCache();
		final DirCache treeO = db.readDirCache();
		final DirCache treeT = db.readDirCache();
		{
			final DirCacheBuilder b = treeB.builder();
			final DirCacheBuilder o = treeO.builder();
			final DirCacheBuilder t = treeT.builder();

			b.add(createEntry("libelf-po/a", FileMode.REGULAR_FILE));
			b.add(createEntry("libelf/c", FileMode.REGULAR_FILE));

			o.add(createEntry("Makefile", FileMode.REGULAR_FILE));
			o.add(createEntry("libelf-po/a", FileMode.REGULAR_FILE));
			o.add(createEntry("libelf/c", FileMode.REGULAR_FILE));

			t.add(createEntry("libelf-po/a", FileMode.REGULAR_FILE));
			t.add(createEntry("libelf/c", FileMode.REGULAR_FILE, "blah"));

			b.finish();
			o.finish();
			t.finish();
		}
