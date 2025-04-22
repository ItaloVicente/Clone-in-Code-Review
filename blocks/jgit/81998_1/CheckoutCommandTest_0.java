	@Test
	public void testNonDeletableFilesOnWindows()
			throws GitAPIException
		org.junit.Assume.assumeTrue(SystemReader.getInstance().isWindows());
		writeTrashFile("toBeModified.txt"
		writeTrashFile("toBeDeleted.txt"
		git.add().addFilepattern(".").call();
		RevCommit addFiles = git.commit().setMessage("add more files").call();

		git.rm().setCached(false).addFilepattern("Test.txt")
				.addFilepattern("toBeDeleted.txt").call();
		writeTrashFile("toBeModified.txt"
		writeTrashFile("toBeCreated.txt"
		git.add().addFilepattern(".").call();
		RevCommit crudCommit = git.commit().setMessage("delete
				.call();
		git.checkout().setName(addFiles.getName()).call();
		try ( FileInputStream fis=new FileInputStream(new File(db.getWorkTree()
			CheckoutCommand coCommand = git.checkout();
			coCommand.setName(crudCommit.getName()).call();
			CheckoutResult result = coCommand.getResult();
			assertEquals(Status.NONDELETED
			assertEquals("[Test.txt
					result.getRemovedList().toString());
			assertEquals("[toBeCreated.txt
					result.getModifiedList().toString());
			assertEquals("[Test.txt]"
			assertTrue(result.getConflictList().isEmpty());
		}
	}

