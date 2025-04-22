			assertEquals(
					"[file.txt
					indexState(CONTENT));
			assertWorkDir(mkmap("file.txt"
			recorder.assertNoEvent();

			git.checkout().setName("b1").call();
			assertEquals("[file.txt
					indexState(CONTENT));
			assertWorkDir(mkmap("file.txt"
			recorder.assertEvent(new String[0]
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
