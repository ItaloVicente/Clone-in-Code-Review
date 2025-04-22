	private static PackFile nameFor(ObjectDirectory odb, ObjectId name,
			String t) {
		File packdir = odb.getPackDirectory();
		return new PackFile(packdir, "pack-" + name.name() + t);
	}

