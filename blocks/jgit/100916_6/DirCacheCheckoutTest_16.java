
			try {
				git.checkout().setName("master").call();
				fail("did not throw exception");
			} catch (Exception e) {
				assertWorkDir(mkmap(fname + "/dir1/file1"
						fname + "/dir2/file2"
			}
			recorder.assertNoEvent();
		} finally {
			if (handle != null) {
				handle.remove();
			}
