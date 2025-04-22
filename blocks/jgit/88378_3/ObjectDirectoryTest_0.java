	@Test
	public void testShallowFile()
			throws Exception {
		FileRepository repository = createBareRepository();
		ObjectDirectory dir = repository.getObjectDatabase();

		String commit = "d3148f9410b071edd4a4c85d2a43d1fa2574b0d2";
		try (PrintWriter writer = new PrintWriter(
				new File(repository.getDirectory()
			writer.println(commit);
		}
		Set<ObjectId> shallowCommits = dir.getShallowCommits();
		assertTrue(shallowCommits.remove(ObjectId.fromString(commit)));
		assertTrue(shallowCommits.isEmpty());

		try (PrintWriter writer = new PrintWriter(
				new File(repository.getDirectory()
			writer.println("X3148f9410b071edd4a4c85d2a43d1fa2574b0d2");
		}
		try {
			dir.getShallowCommits();
			Assert.fail();
		} catch (IOException ex) {
		}
	}

