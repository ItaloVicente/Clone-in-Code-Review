			count = 0;
			for (RevCommit c : git.log().addPath("subdir-include").call()) {
				assertEquals("commit3"
				count++;
			}
			assertEquals(1

			count = 0;
			for (RevCommit c : git.log().excludePath("subdir-exclude").call()) {
				count++;
			}
			assertEquals(3

