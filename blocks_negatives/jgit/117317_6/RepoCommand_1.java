				parser.read(inputStream);
				for (RepoProject proj : parser.getFilteredProjects()) {
					addSubmodule(proj.getUrl(),
							proj.getPath(),
							proj.getRevision(),
							proj.getCopyFiles(),
							proj.getLinkFiles(),
							proj.getGroups(),
							proj.getRecommendShallow());
				}
			} catch (GitAPIException | IOException e) {
				throw new ManifestErrorException(e);
