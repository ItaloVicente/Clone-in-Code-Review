	@Test
	public void testStatusPorcelain() throws Exception {
		Git git = new Git(db);
		writeTrashFile("tracked", "tracked");
		writeTrashFile("stagedNew", "stagedNew");
		writeTrashFile("stagedModified", "stagedModified");
		writeTrashFile("stagedDeleted", "stagedDeleted");
		writeTrashFile("trackedModified", "trackedModified");
		writeTrashFile("trackedDeleted", "trackedDeleted");
		writeTrashFile("untracked", "untracked");
