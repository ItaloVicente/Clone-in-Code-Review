			assertEquals(2
					resetHardAndCount(initial)
					"Should have entered src/newignored directory");
			assertEquals(2
					resetHardAndCount(commit)
					"Should have entered src/newignored directory");
			assertEquals(1
					resetHardAndCount(top)
					"Should not have entered src/newignored directory");
