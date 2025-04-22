		if (useAnnotatedTags || describeUseAllTags) {
			assertEquals("t1-1-g0244e7f"
		}

		ObjectId c4 = merge(c2);

		assertNameStartsWith(c4
		if (describeUseAllTags) {
			assertEquals(
					"2 commits for describe commit increment expected since leightweight tag: c4 and c3"
					"t2-2-g119892b"
		} else if (!useAnnotatedTags && !describeUseAllTags) {
			assertEquals("no matching commits expected"
					null
					describe(c4));
		} else {
			assertEquals(
					"3 commits for describe commit increment expected since annotated tag: c4 and c3 and c2"
					"t1-3-g119892b"
		}
