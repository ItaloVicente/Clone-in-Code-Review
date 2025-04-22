				assertEquals("merge -X " + contentStrategy.name(),
						MergeStatus.CONFLICTING, result.getMergeStatus());

				assertTrue("merge -X " + contentStrategy.name(),
						new File(db.getWorkTree(), "a").exists());
				assertEquals("merge -X " + contentStrategy.name(),
						"1\na(main)\n3\n",
						read(new File(db.getWorkTree(), "a")));
				assertEquals("merge -X " + contentStrategy.name(), "1\nb\n3\n",
						read(new File(db.getWorkTree(), "b")));

				assertNotNull("merge -X " + contentStrategy.name(),
						result.getConflicts());
				assertEquals("merge -X " + contentStrategy.name(), 1,
						result.getConflicts().size());
				assertEquals("merge -X " + contentStrategy.name(), 3,
						result.getConflicts().get("a")[0].length);
