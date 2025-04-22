
	public void test005_todopack() throws IOException {
		final File todopack = JGitTestUtil.getTestResourceFile("todopack");
		if (!todopack.isDirectory()) {
			System.err.println("Skipping " + getName() + ": no " + todopack);
			return;
		}

		final File packDir = new File(db.getObjectsDirectory(), "pack");
		final String packname = "pack-2e71952edc41f3ce7921c5e5dd1b64f48204cf35";
		copyFile(new File(todopack, packname + ".pack"), new File(packDir,
				packname + ".pack"));
		copyFile(new File(todopack, packname + ".idx"), new File(packDir,
				packname + ".idx"));
		Tree t;

		t = db
				.mapTree(ObjectId.fromString(
						"aac9df07f653dd18b935298deb813e02c32d2e6f"));
		assertNotNull(t);
		t.memberCount();

		t = db
				.mapTree(ObjectId.fromString(
						"6b9ffbebe7b83ac6a61c9477ab941d999f5d0c96"));
		assertNotNull(t);
		t.memberCount();
	}
