		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"plugins/cookbook\" name=\"plugins/cookbook\" />")
			.append("</manifest>");
		RepoCommand cmd = new RepoCommand(dest);

		IndexedRepos repos = new IndexedRepos();
		repos.put("plugins/cookbook", child);

		RevCommit commit = cmd
			.setInputStream(new ByteArrayInputStream(xmlContent.toString().getBytes(CHARSET)))
			.setRemoteReader(repos)
			.setURI("")
			.setTargetURI("gerrit")
			.setRecordRemoteBranch(true)
			.setRecordSubmoduleLabels(true)
			.call();

		String idStr = commit.getId().name() + ":" + ".gitmodules";
		ObjectId modId = dest.resolve(idStr);
