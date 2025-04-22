				assertEquals(2, generator.getSourceStart());
				assertEquals(3, generator.getSourceEnd());
				assertEquals(INTERESTING_FILE, generator.getSourcePath());

				assertTrue(generator.next());
				assertEquals(filteredC1, generator.getSourceCommit());
				assertEquals(2, generator.getRegionLength());
				assertEquals(0, generator.getResultStart());
				assertEquals(2, generator.getResultEnd());
