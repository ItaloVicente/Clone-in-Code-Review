			if (all && !repo.isBare() && repo.getWorkDir() != null) {
				Git git = new Git(repo);
				try {
					git.add()
							.addFilepattern(".")
							.setUpdate(true).call();
				} catch (NoFilepatternException e) {
					throw new JGitInternalException(e.getMessage()
				}
			}

