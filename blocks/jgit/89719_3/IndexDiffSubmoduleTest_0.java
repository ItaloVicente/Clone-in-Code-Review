		try (Git submoduleStandaloneGit = new Git(submoduleStandalone)) {
			submoduleStandaloneGit.add().addFilepattern("fileInSubmodule")
					.call();
			submoduleStandaloneGit.commit().setMessage("add file to submodule")
					.call();

			submodule_db = (FileRepository) Git.wrap(db).submoduleAdd()
					.setPath("modules/submodule").setURI(submoduleStandalone
							.getDirectory().toURI().toString())
					.call();
		}
