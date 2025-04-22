			if (d.getPackSource() == GC) {
				gcPackFound = true;
				assertTrue("has commit0"
				assertFalse("no commit1"
			} else if (d.getPackSource() == UNREACHABLE_GARBAGE) {
				garbagePackFound = true;
				assertFalse("no commit0"
				assertTrue("has commit1"
			} else {
				fail("unexpected " + d.getPackSource());
