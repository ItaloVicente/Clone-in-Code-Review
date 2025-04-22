		try (Repository child = cloneRepository(groupADb
				Repository dest = cloneRepository(db
			StringBuilder xmlContent = new StringBuilder();
			xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
					.append("<manifest>")
					.append("<remote name=\"remote1\" fetch=\"..\" />")
					.append("<default revision=\"master\" remote=\"remote1\" />")
					.append("<project path=\"base\" name=\"platform/base\" />")
					.append("</manifest>");
			RepoCommand cmd = new RepoCommand(dest);
