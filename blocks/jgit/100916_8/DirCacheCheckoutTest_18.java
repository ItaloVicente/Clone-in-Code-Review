
			try {
				git.checkout().setName("master").call();
				fail("did not throw exception");
			} catch (Exception e) {
				assertWorkDir(mkmap(fname
						linkName + "/dir2/file2"
			}
			recorder.assertNoEvent();
		} finally {
			if (handle != null) {
				handle.remove();
			}
