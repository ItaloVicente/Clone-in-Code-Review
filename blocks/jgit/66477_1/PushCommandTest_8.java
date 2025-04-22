		try (Git git = new Git(db);
				Git git2 = new Git(createBareRepository())) {
			final StoredConfig config = git.getRepository().getConfig();
			RemoteConfig remoteConfig = new RemoteConfig(config
			URIish uri = new URIish(git2.getRepository().getDirectory().toURI()
					.toURL());
			remoteConfig.addURI(uri);
			remoteConfig.addPushRefSpec(new RefSpec("HEAD:refs/heads/newbranch"));
			remoteConfig.update(config);
			config.save();

			writeTrashFile("f"
			git.add().addFilepattern("f").call();
			RevCommit commit = git.commit().setMessage("adding f").call();

			assertEquals(null
			git.push().setRemote("test").call();
			assertEquals(commit.getId()
					git2.getRepository().resolve("refs/heads/newbranch"));
		}
