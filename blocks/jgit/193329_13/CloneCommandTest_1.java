    @Test
    public void testCloneRepositoryWithDepth() throws IOException
		File directory = createTempDirectory("testCloneRepositoryWithDepth");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
        command.setDepth(1);
		command.setBranchesToClone(Set.of("refs/heads/test"));
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());

		List<RevCommit> log = StreamSupport.stream(git2.log().all().call().spliterator()
				.collect(Collectors.toList());
		assertEquals(1
		RevCommit commit = log.get(0);
		assertEquals(Set.of(commit.getId())
				git2.getRepository().getObjectDatabase().getShallowCommits());
		assertEquals("Second commit"
		assertEquals(0
	}

	@Test
	public void testCloneRepositoryWithDepthAndAllBranches() throws IOException
		File directory = createTempDirectory("testCloneRepositoryWithDepthAndAllBranches");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		command.setDepth(1);
		command.setCloneAllBranches(true);
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());

		List<RevCommit> log = StreamSupport.stream(git2.log().all().call().spliterator()
				.collect(Collectors.toList());
		assertEquals(2
		assertEquals(log.stream().map(RevCommit::getId).collect(Collectors.toSet())
				git2.getRepository().getObjectDatabase().getShallowCommits());
		assertEquals(List.of("Second commit"
				log.stream().map(RevCommit::getFullMessage).collect(Collectors.toList()));
		for (RevCommit commit : log) {
			assertEquals(0
		}
	}

	@Test
	public void testCloneRepositoryWithDepth2() throws Exception {
		RevCommit parent = tr.git().log().call().iterator().next();
		RevCommit commit = tr.commit()
				.parent(parent)
				.message("Third commit")
				.add("test.txt"
				.create();
		tr.update("refs/heads/test"

		File directory = createTempDirectory("testCloneRepositoryWithDepth2");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		command.setDepth(2);
		command.setBranchesToClone(Set.of("refs/heads/test"));
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());

		List<RevCommit> log = StreamSupport
				.stream(git2.log().all().call().spliterator()
				.collect(Collectors.toList());
		assertEquals(2
		assertEquals(Set.of(parent.getId())
				git2.getRepository().getObjectDatabase().getShallowCommits());
		assertEquals(List.of("Third commit"
				.map(RevCommit::getFullMessage).collect(Collectors.toList()));
		assertEquals(List.of(Integer.valueOf(1)
				log.stream().map(RevCommit::getParentCount)
						.collect(Collectors.toList()));
	}

	@Test
	public void testCloneRepositoryWithDepthAndFetch() throws Exception {
		File directory = createTempDirectory("testCloneRepositoryWithDepthAndFetch");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		command.setDepth(1);
		command.setBranchesToClone(Set.of("refs/heads/test"));
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());

		RevCommit parent = tr.git().log().call().iterator().next();
		RevCommit commit = tr.commit()
				.parent(parent)
				.message("Third commit")
				.add("test.txt"
				.create();
		tr.update("refs/heads/test"

		git2.fetch().call();

		List<RevCommit> log = StreamSupport
				.stream(git2.log().all().call().spliterator()
				.collect(Collectors.toList());
		assertEquals(2
		assertEquals(Set.of(parent.getId())
				git2.getRepository().getObjectDatabase().getShallowCommits());
		assertEquals(List.of("Third commit"
				.map(RevCommit::getFullMessage).collect(Collectors.toList()));
		assertEquals(List.of(Integer.valueOf(1)
				log.stream().map(RevCommit::getParentCount)
						.collect(Collectors.toList()));
	}

	@Test
	public void testCloneRepositoryWithDepthAndFetchWithDepth() throws Exception {
		File directory = createTempDirectory("testCloneRepositoryWithDepthAndFetchWithDepth");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		command.setDepth(1);
		command.setBranchesToClone(Set.of("refs/heads/test"));
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());

		RevCommit parent = tr.git().log().call().iterator().next();
		RevCommit commit = tr.commit()
				.parent(parent)
				.message("Third commit")
				.add("test.txt"
				.create();
		tr.update("refs/heads/test"

		git2.fetch().setDepth(1).call();

		List<RevCommit> log = StreamSupport
				.stream(git2.log().all().call().spliterator()
				.collect(Collectors.toList());
		assertEquals(2
		assertEquals(
				log.stream().map(RevObject::getId).collect(Collectors.toSet())
				git2.getRepository().getObjectDatabase().getShallowCommits());
		assertEquals(List.of("Third commit"
				.map(RevCommit::getFullMessage).collect(Collectors.toList()));
		assertEquals(List.of(Integer.valueOf(0)
				log.stream().map(RevCommit::getParentCount)
						.collect(Collectors.toList()));
	}

	@Test
	public void testCloneRepositoryWithDepthAndFetchUnshallow() throws Exception {
		File directory = createTempDirectory("testCloneRepositoryWithDepthAndFetchUnshallow");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		command.setDepth(1);
		command.setBranchesToClone(Set.of("refs/heads/test"));
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());

		git2.fetch().setUnshallow(true).call();

		List<RevCommit> log = StreamSupport
				.stream(git2.log().all().call().spliterator()
				.collect(Collectors.toList());
		assertEquals(2
		assertEquals(Set.of()
				git2.getRepository().getObjectDatabase().getShallowCommits());
		assertEquals(List.of("Second commit"
				.map(RevCommit::getFullMessage).collect(Collectors.toList()));
		assertEquals(List.of(Integer.valueOf(1)
				log.stream().map(RevCommit::getParentCount)
						.collect(Collectors.toList()));
	}

    @Test
	public void testCloneRepositoryWithShallowSince() throws Exception {
		RevCommit commit = tr.commit()
				.parent(tr.git().log().call().iterator().next())
				.message("Third commit").add("test.txt"
				.create();
		tr.update("refs/heads/test"

        File directory = createTempDirectory("testCloneRepositoryWithShallowSince");
        CloneCommand command = Git.cloneRepository();
        command.setDirectory(directory);
        command.setURI(fileUri());
        command.setShallowSince(Instant.ofEpochSecond(commit.getCommitTime()));
        command.setBranchesToClone(Set.of("refs/heads/test"));
        Git git2 = command.call();
        addRepoToClose(git2.getRepository());

		List<RevCommit> log = StreamSupport
				.stream(git2.log().all().call().spliterator()
				.collect(Collectors.toList());
		assertEquals(1
		assertEquals(Set.of(commit.getId())
				git2.getRepository().getObjectDatabase().getShallowCommits());
		assertEquals("Third commit"
		assertEquals(0
    }

	@Test
	public void testCloneRepositoryWithShallowExclude() throws Exception {
		RevCommit parent = tr.git().log().call().iterator().next();
		tr.update("refs/heads/test"
				tr.commit()
					.parent(parent)
					.message("Third commit")
					.add("test.txt"
					.create());

		File directory = createTempDirectory("testCloneRepositoryWithShallowExclude");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		command.addShallowExclude(parent.getId());
		command.setBranchesToClone(Set.of("refs/heads/test"));
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());

		List<RevCommit> log = StreamSupport
				.stream(git2.log().all().call().spliterator()
				.collect(Collectors.toList());
		assertEquals(1
		RevCommit commit = log.get(0);
		assertEquals(Set.of(commit.getId())
				git2.getRepository().getObjectDatabase().getShallowCommits());
		assertEquals("Third commit"
		assertEquals(0
	}

