		String firstIdStr = commit.getId().name() + ":" + ".gitmodules";
		commit = new RepoCommand(dest)
			.setInputStream(new ByteArrayInputStream(
				xmlContent.toString().getBytes(CHARSET)))
			.setRemoteReader(repos)
			.setURI("platform/")
			.setTargetURI("platform/superproject")
			.setRecordRemoteBranch(true)
			.setRecordSubmoduleLabels(true)
			.call();
		String idStr = commit.getId().name() + ":" + ".gitmodules";
		assertEquals(firstIdStr, idStr);
		child.close();
		dest.close();
