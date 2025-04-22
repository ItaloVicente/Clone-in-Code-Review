	@Test
	public void testCloneRepository_refLogForLocalRefs() throws IOException
			JGitInternalException
		File directory = createTempDirectory("testCloneRepository");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		Repository clonedRepo = git2.getRepository();
		addRepoToClose(clonedRepo);

		List<Ref> clonedRefs = clonedRepo.getRefDatabase().getRefs();
		Stream<Ref> remoteRefs = clonedRefs.stream().filter(CloneCommandTest::isRemote);
		Stream<Ref> localHeadsRefs = clonedRefs.stream().filter(CloneCommandTest::isLocalHead);

		remoteRefs.forEach(ref -> assertFalse("Ref " + ref.getName() + " is remote and should not have a reflog"
		localHeadsRefs.forEach(ref -> assertTrue("Ref " + ref.getName() + " is local head and should have a reflog"
	}

	private static boolean isRemote(Ref ref) {
		return ref.getName().startsWith(Constants.R_REMOTES);
	}

	private static boolean isLocalHead(Ref ref) {
		return !isRemote(ref) && ref.getName().startsWith(Constants.R_HEADS);
	}

	private static boolean hasRefLog(Repository repo
		try {
			return repo.getReflogReader(ref.getName()).getLastEntry() != null;
		} catch (IOException ioe) {
			throw new IllegalStateException(ioe);
		}
	}

