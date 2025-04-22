			count = 0;
			for (RevCommit c : git.log().addPath("subdir-include").call()) {
				assertEquals("commit3"
				count++;
			}
			assertEquals(1

			count = 0;
			Iterator it = git.log()
				.excludePath("subdir-exclude")
				.call()
				.iterator();
			while (it.hasNext()) {
				it.next();
				count++;
			}
			assertEquals(3

