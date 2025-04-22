		git.add().addFilepattern("a.txt").addFilepattern("b.txt").call();
		modTimes.add(db.getIndexFile().lastModified());
		assertEquals(
				"[a.txt
				"[b.txt
				indexState(CONTENT_ID));
