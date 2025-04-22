		if (useAnnotatedTags) {
			assertEquals("bob-t2-1-g3e563c5"
			assertEquals("bob-t2-1-g3e563c5"
					git.describe().setTags(false).call());
			assertEquals("bob-t2-1-g3e563c5"
					git.describe().setTags(true).call());
		} else {
			assertEquals(null
			assertEquals(null
			assertEquals("bob-t2-1-g3e563c5"
					git.describe().setTags(true).call());
		}
