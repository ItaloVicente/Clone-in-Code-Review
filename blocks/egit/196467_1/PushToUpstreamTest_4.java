	@Test
	public void pushWithHook() throws Exception {
		checkoutNewLocalBranch("foo");
		String remoteName = "origin";
		String pushUrl = repository.getConfig().getString("remote", "push",
				"pushurl");
		repository.getConfig().setString("remote", remoteName, "url", pushUrl);
		repository.getConfig().setString("remote", remoteName, "fetch",
				"refs/heads/*:refs/remotes/origin/*");
		File gitDir = repository.getDirectory();
		File hookDir = new File(gitDir, "hooks");
		assertTrue(hookDir.mkdir() || hookDir.isDirectory());
		File hookFile = new File(hookDir, "pre-push");
		Files.writeString(hookFile.toPath(), "#!/bin/sh\n"
				+ "echo \"1:$1 2:$2 3:$3\"\n" // to stdout
				+ "cat - 1>&2\n" // to stderr
				+ "exit 0\n");
		if (repository.getFS().supportsExecute()) {
			repository.getFS().setExecute(hookFile, true);
		}
		String headId = repository.resolve(Constants.HEAD).getName();
		String forUri = MessageFormat.format(CoreText.PushOperation_ForUri,
				pushUrl);
		String expectedHookOutput = MessageFormat.format(
				UIText.PushResultTable_PrePushHookOutput,
				"stdout: " + forUri+ '\n'
				+ "stdout: 1:" + pushUrl + " 2:" + pushUrl + " 3:\n",
				"stderr: " + forUri + '\n'
				+ "stderr: refs/heads/foo " + headId
				+ " refs/heads/foo "+ ObjectId.zeroId().getName() + '\n');
		String resultText = pushToUpstream("origin", "foo", true, false);
		assertEquals("Hook message doesn't match: " + resultText,
				expectedHookOutput,
				resultText.substring(0, Math.min(resultText.length(),
						expectedHookOutput.length())));

		assertBranchPushed("foo", remoteRepository);
	}

