			tw.addTree(commit1.getTree());
			tw.addTree(commit2.getTree());
			List<DiffEntry> scan = DiffEntry.scan(tw);
			assertEquals(1
			assertEquals(FileMode.MISSING
			assertEquals(FileMode.SYMLINK
		}
