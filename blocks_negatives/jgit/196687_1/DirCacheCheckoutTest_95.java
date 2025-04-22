			assertEquals("Should have entered src/newignored directory", 2,
					resetHardAndCount(initial));
			assertEquals("Should have entered src/newignored directory", 2,
					resetHardAndCount(commit));
			assertEquals("Should not have entered src/newignored directory", 1,
					resetHardAndCount(top));
