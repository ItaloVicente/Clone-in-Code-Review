		Git submoduleStandaloneGit = Git.wrap(submoduleStandalone);
		submoduleStandaloneGit.add().addFilepattern("fileInSubmodule").call();
		submoduleStandaloneGit.commit().setMessage("add file to submodule")
				.call();

		submodule_db = (FileRepository) Git.wrap(db).submoduleAdd()
				.setPath("modules/submodule")
				.setURI(submoduleStandalone.getDirectory().toURI().toString())
				.call();
		submoduleStandalone.close();
