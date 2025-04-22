			LockFile lf = new LockFile(db.getIndexFile()
			assertTrue(lf.lock());
			try {
				git.checkout().setName(commit1.name()).call();
				fail("JGitInternalException not thrown");
			} catch (JGitInternalException e) {
				assertTrue(e.getCause() instanceof LockFailedException);
				lf.unlock();
				git.checkout().setName(commit1.name()).call();
			}
