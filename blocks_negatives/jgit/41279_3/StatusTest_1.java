				"On branch master", //
						"Changes to be committed:", //
						"", //
						"\tnew file:   stagedDeleted", //
						"\tnew file:   stagedModified", //
						"\tnew file:   tracked", //
						"\tnew file:   trackedDeleted", //
						"\tnew file:   trackedModified", //
						"", //
						"Untracked files:", //
						"", //
						"\tstagedNew", //
						"\tuntracked", //
				}, execute("git status")); //
		git.commit().setMessage("initial commit")
				.call();
				"On branch master", //
						"Untracked files:", //
						"", //
						"\tstagedNew", //
						"\tuntracked", //
				}, execute("git status")); //
