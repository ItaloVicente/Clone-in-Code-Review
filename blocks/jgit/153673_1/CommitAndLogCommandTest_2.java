			assertEquals(4

			count = 0;
			for (RevCommit c : git.log()
				.addPath("subdir-include")
				.excludePath("subdir-exclude").call()) {
				count++;
			}
			assertEquals(1

			count = 0;
			for (RevCommit c : git.log()
				.addPath("subdir-exclude")
				.excludePath("subdir-exclude").call()) {
				count++;
			}
			assertEquals(0

			count = 0;
			for (RevCommit c : git.log()
				.excludePath("b.txt")
				.excludePath("subdir-exclude").call()) {
				count++;
			}
