				parser.read(inputStream);
				for (RepoProject proj : parser.getFilteredProjects()) {
					addSubmodule(proj.url
							proj.path
							proj.getRevision()
							proj.copyfiles);
				}
			} catch (GitAPIException | IOException e) {
