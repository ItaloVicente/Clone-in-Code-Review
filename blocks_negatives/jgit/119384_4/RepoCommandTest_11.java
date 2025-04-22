				String fetchUrl = fetchSlash ? abs + "/" : abs;
				String baseUrl = baseSlash ? abs + "/" : abs;

				StringBuilder xmlContent = new StringBuilder();
				xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
					.append("<manifest>")
					.append("<remote name=\"origin\" fetch=\"" + fetchUrl + "\" />")
					.append("<default revision=\"master\" remote=\"origin\" />")
					.append("<project path=\"src\" name=\"chromium/src\" />")
					.append("</manifest>");
				RepoCommand cmd = new RepoCommand(dest);

				IndexedRepos repos = new IndexedRepos();
				repos.put(repoUrl, child);

				RevCommit commit = cmd
					.setInputStream(new ByteArrayInputStream(xmlContent.toString().getBytes(CHARSET)))
					.setRemoteReader(repos)
					.setURI(baseUrl)
					.setTargetURI("gerrit")
					.setRecordRemoteBranch(true)
					.setRecordSubmoduleLabels(true)
					.call();

				String idStr = commit.getId().name() + ":" + ".gitmodules";
				ObjectId modId = dest.resolve(idStr);

				try (ObjectReader reader = dest.newObjectReader()) {
					byte[] bytes = reader.open(modId).getCachedBytes(Integer.MAX_VALUE);
					Config base = new Config();
					BlobBasedConfig cfg = new BlobBasedConfig(base, bytes);
					String subUrl = cfg.getString("submodule", "src", "url");
				}
				fetchSlash = !fetchSlash;
			} while (fetchSlash);
			baseSlash = !baseSlash;
		} while (baseSlash);
		child.close();
		dest.close();
