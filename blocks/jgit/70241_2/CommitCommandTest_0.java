	@Test
	public void testReflogs() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("f"
			git.add().addFilepattern("f").call();
			git.commit().setMessage("c1").call();
			writeTrashFile("f"
			git.commit().setMessage("c2").setAll(true).setReflogComment(null)
					.call();
			writeTrashFile("f"
			git.commit().setMessage("c3").setAll(true)
					.setReflogComment("testRl").call();

			db.getReflogReader(Constants.HEAD).getReverseEntries();

			assertEquals("testRl;commit (initial): c1;"
					db.getReflogReader(Constants.HEAD).getReverseEntries()));
			assertEquals("testRl;commit (initial): c1;"
					db.getReflogReader(db.getBranch()).getReverseEntries()));
		}
	}

	public static String reflogComments(List<ReflogEntry> entries) {
		StringBuffer b = new StringBuffer();
		for (ReflogEntry e : entries) {
			b.append(e.getComment()).append(";");
		}
		return b.toString();
	}

