	@Test
	public void emptyRefDirectoryDeleted() throws Exception {
		String ref = "dir/ref";
		tr.branch(ref).commit().create();
		String name = repo.findRef(ref).getName();
		Path dir = repo.getDirectory().toPath().resolve(name).getParent();

		gc.packRefs();
		assertFalse(Files.exists(dir));
	}

