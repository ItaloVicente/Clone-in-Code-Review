		try (Git remoteGit = new Git(remoteRepository)) {
			writeTrashFile("Test.txt"
			remoteGit.add().addFilepattern("Test.txt").call();
			initialCommit = remoteGit.commit().setMessage("Initial commit").call();
			writeTrashFile("Test.txt"
			remoteGit.add().addFilepattern("Test.txt").call();
			secondCommit = remoteGit.commit().setMessage("Second commit").call();
			RefUpdate rup = remoteRepository.updateRef("refs/heads/master");
			rup.setNewObjectId(initialCommit.getId());
			rup.forceUpdate();

			Repository localRepository = createWorkRepository();
			Git localGit = new Git(localRepository);
			StoredConfig config = localRepository.getConfig();
			RemoteConfig rc = new RemoteConfig(config
			rc.addURI(new URIish(remoteRepository.getDirectory().getAbsolutePath()));
			rc.update(config);
			config.save();
			FetchResult res = localGit.fetch().setRemote("origin").call();
			assertFalse(res.getTrackingRefUpdates().isEmpty());
			rup = localRepository.updateRef("refs/heads/master");
			rup.setNewObjectId(initialCommit.getId());
			rup.forceUpdate();
			rup = localRepository.updateRef(Constants.HEAD);
			rup.link("refs/heads/master");
			rup.setNewObjectId(initialCommit.getId());
			rup.update();
			return localGit;
		}
