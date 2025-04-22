	public void testList() throws IOException {
		if (skipTest())
			return;

		final File a = new File(root
		final File b = new File(root
		final File d = new File(root
		write(a
		access.symlink(b
		assertTrue("created " + d

		DirEnt[] ent = access.list(root);
		assertEquals(4

		Arrays.sort(ent
			public int compare(DirEnt a
				return a.getName().compareTo(b.getName());
			}
		});

		DirEnt ent_git = ent[0];
		DirEnt ent_a = ent[1];
		DirEnt ent_b = ent[2];
		DirEnt ent_d = ent[3];

		assertEquals(".git"
		assertEquals("a"
		assertEquals("b"
		assertEquals("d"

		assertEquals(DirEnt.TYPE_DIRECTORY
		assertEquals(DirEnt.TYPE_FILE
		assertEquals(DirEnt.TYPE_SYMLINK
		assertEquals(DirEnt.TYPE_DIRECTORY
	}

