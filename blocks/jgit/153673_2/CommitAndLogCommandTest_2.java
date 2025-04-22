			assertEquals(4

			count = 0;
			it = git.log()
				.addPath("subdir-include")
				.excludePath("subdir-exclude")
				.call()
				.iterator();
			while (it.hasNext()) {
				it.next();
				count++;
			}
			assertEquals(1

			count = 0;
			it = git.log()
				.addPath("subdir-exclude")
				.excludePath("subdir-exclude")
				.call()
				.iterator();

			while (it.hasNext()) {
				it.next();
				count++;
			}
			assertEquals(0

			count = 0;
			it = git.log()
				.excludePath("b.txt")
				.excludePath("subdir-exclude")
				.call()
				.iterator();

			while (it.hasNext()) {
				it.next();
				count++;
			}
