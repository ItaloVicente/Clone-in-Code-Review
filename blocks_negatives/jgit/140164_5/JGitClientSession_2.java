			if (d.getPackSource() == GC) {
				gcPackFound = true;
				assertTrue("has commit0", isObjectInPack(commit0, pack));
				assertFalse("no commit1", isObjectInPack(commit1, pack));
			} else if (d.getPackSource() == UNREACHABLE_GARBAGE) {
				garbagePackFound = true;
				assertFalse("no commit0", isObjectInPack(commit0, pack));
				assertTrue("has commit1", isObjectInPack(commit1, pack));
			} else {
				fail("unexpected " + d.getPackSource());
			}
