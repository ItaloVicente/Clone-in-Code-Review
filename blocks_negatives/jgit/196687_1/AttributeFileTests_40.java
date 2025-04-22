			assertEquals("Unexpected index state",
					"[.gitattributes, mode:100644, content:* text=auto]"
							+ "[dummy.txt, mode:100644, content:" + content
							+ ']',
					indexState(CONTENT));
			assertTrue("Should be able to delete " + dummy, dummy.delete());
