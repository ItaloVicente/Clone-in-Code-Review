	@Test
	public void testList() throws IOException {
		if (skipTest())
			return;

		final File a = new File(root
		final File b = new File(root
		final File d = new File(root
		write(a
		access.symlink(b
		assertTrue("created " + d

		for (int i = 0; i < 116; i++) {
			final String name = fileName(i);
			final File f = new File(root
			write(f
		}
		DirEnt[] ent = access.list(root);
		assertEquals(120

		Arrays.sort(ent
			public int compare(DirEnt d1
				return d1.getName().compareTo(d2.getName());
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

		for (int i = 4; i < 120; i++) {
			DirEnt e = ent[i];
			assertEquals(fileName(i - 4)
			assertEquals(DirEnt.TYPE_FILE
		}
	}

