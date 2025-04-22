		git.add().addFilepattern("sub").call();
		modTimes.add(db.getIndexFile().lastModified());
		assertEquals(
				"[sub/a.txt
				"[sub/b.txt
				indexState(CONTENT_ID));
