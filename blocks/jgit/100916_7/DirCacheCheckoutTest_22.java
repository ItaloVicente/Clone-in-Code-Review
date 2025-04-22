			assertEquals(
					"[file.txt
					indexState(CONTENT));
			assertWorkDir(mkmap("file.txt"
			recorder.assertNoEvent();

			git.checkout().setName("b1").call();
			assertEquals("[file.txt
					indexState(CONTENT));
			assertWorkDir(mkmap("file.txt"
			recorder.assertEvent(ChangeRecorder.EMPTY
					new String[] { "file2.txt" });
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
