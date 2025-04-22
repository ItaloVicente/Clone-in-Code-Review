			try {
				git.checkout().setName(branch_1.getName()).call();
				fail("Don't get the expected conflict");
			} catch (CheckoutConflictException e) {
				assertEquals("[a]"
				entry = new FileTreeIterator.FileEntry(
						new File(db.getWorkTree()
				assertEquals(FileMode.REGULAR_FILE
			}
