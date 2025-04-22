				assertEquals(2, generator.getResultStart());
				assertEquals(3, generator.getResultEnd());
				assertEquals(2, generator.getSourceStart());
				assertEquals(3, generator.getSourceEnd());
				assertEquals(INTERESTING_FILE, generator.getSourcePath());

				assertTrue(generator.next());
				assertEquals(c1, generator.getSourceCommit());
				assertEquals(2, generator.getRegionLength());
