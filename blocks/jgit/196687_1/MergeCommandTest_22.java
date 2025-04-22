				assertEquals(MergeStatus.CONFLICTING

				assertTrue(new File(db.getWorkTree()
						"merge -X " + contentStrategy.name());
				assertEquals("1\na(main)\n3\n"
						read(new File(db.getWorkTree()
						"merge -X " + contentStrategy.name());
				assertEquals("1\nb\n3\n"
						read(new File(db.getWorkTree()
						"merge -X " + contentStrategy.name());

				assertNotNull(result.getConflicts()
						"merge -X " + contentStrategy.name());
				assertEquals(1
						result.getConflicts().size()
						"merge -X " + contentStrategy.name());
				assertEquals(3
						result.getConflicts().get("a")[0].length
						"merge -X " + contentStrategy.name());
