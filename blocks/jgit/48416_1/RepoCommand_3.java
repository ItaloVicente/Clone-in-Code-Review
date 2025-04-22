				parser.read(inputStream);
				for (Project proj : parser.getFilteredProjects()) {
					addSubmodule(proj.url
							proj.path
							proj.getRevision()
							proj.copyfiles);
				}
			} catch (GitAPIException | IOException e) {
