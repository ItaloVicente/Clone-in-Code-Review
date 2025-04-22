	@Test
	public void objectSizeIdx_reachableBlob_bigEnough_indexed() throws Exception {
		String master = "refs/heads/master";
		RevCommit root = git.branch(master).commit().message("root").noParents()
				.create();
		RevBlob headsBlob = git.blob("twelve bytes");
		git.branch(master).commit()
				.message("commit on head")
				.add("file.txt"
				.parent(root)
				.create();

		gcWithObjectSizeIndex(10);

		DfsReader reader = odb.newReader();
		DfsPackFile gcPack = findFirstBySource(odb.getPacks()
		assertTrue(gcPack.hasObjSizeIndex(reader));
		assertEquals(12
	}

	@Test
	public void objectSizeIdx_reachableBlob_tooSmall_notIndexed() throws Exception {
		String master = "refs/heads/master";
		RevCommit root = git.branch(master).commit().message("root").noParents()
				.create();
		RevBlob tooSmallBlob = git.blob("small");
		git.branch(master).commit()
				.message("commit on head")
				.add("small.txt"
				.parent(root)
				.create();

		gcWithObjectSizeIndex(10);

		DfsReader reader = odb.newReader();
		DfsPackFile gcPack = findFirstBySource(odb.getPacks()
		assertTrue(gcPack.hasObjSizeIndex(reader));
		assertEquals(-1
	}

	@Test
	public void objectSizeIndex_unreachableGarbage_noIdx() throws Exception {
		String master = "refs/heads/master";
		RevCommit root = git.branch(master).commit().message("root").noParents()
				.create();
		git.branch(master).commit()
				.message("commit on head")
				.add("file.txt"
				.parent(root)
				.create();
		git.update(master
		gcWithObjectSizeIndex(0);

		DfsReader reader = odb.newReader();
		DfsPackFile gcRestPack = findFirstBySource(odb.getPacks()
		assertFalse(gcRestPack.hasObjSizeIndex(reader));
	}

	private static DfsPackFile findFirstBySource(DfsPackFile[] packs
		return Arrays.stream(packs)
				.filter(p -> p.getPackDescription().getPackSource() == source)
				.findFirst().get();
	}

