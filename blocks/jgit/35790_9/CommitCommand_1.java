			if (!noVerify) {
				repo.getFS().writeToFile(message
						repo.getCommitEditMessageFile());
				String path = repo.getCommitEditMessageFile().getAbsolutePath();
				path = path.replace(File.separatorChar
				Hooks.commitMsg(repo).setOutputStream(hookOutputStream)
						.setParameters(path).run();
				setMessage(repo.getFS().readFileContent(
						repo.getCommitEditMessageFile()));
			}

