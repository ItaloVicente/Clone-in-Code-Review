		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\"..\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"base\" name=\"platform/base\" />")
			.append("</manifest>");
		RepoCommand cmd = new RepoCommand(dest);

		IndexedRepos repos = new IndexedRepos();
		repos.put("platform/base", child);

		RevCommit commit = cmd
			.setInputStream(new ByteArrayInputStream(
				xmlContent.toString().getBytes(CHARSET)))
			.setRemoteReader(repos)
			.setURI("platform/")
			.setTargetURI("platform/superproject")
			.setRecordRemoteBranch(true)
			.setRecordSubmoduleLabels(true)
			.call();
