		final ObjectDirectory odb = (ObjectDirectory) db.getObjectDatabase();
		final PackWriter pw = new PackWriter(db, NullProgressMonitor.INSTANCE);

		Set<ObjectId> all = new HashSet<ObjectId>();
		for (Ref r : db.getAllRefs().values())
			all.add(r.getObjectId());
		pw.preparePack(all, Collections.<ObjectId> emptySet());

		final ObjectId name = pw.computeName();
		OutputStream out;

		final File pack = nameFor(odb, name, ".pack");
		out = new BufferedOutputStream(new FileOutputStream(pack));
		try {
			pw.writePack(out);
		} finally {
			out.close();
		}
		pack.setReadOnly();
