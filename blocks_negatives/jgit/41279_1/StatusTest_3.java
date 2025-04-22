				"On branch master", //
						"Untracked files:", //
						"", //
						"\tuntracked", //
				}, execute("git status")); //
		git.merge().include(testBranch.getId()).call();
				"On branch master", //
						"Unmerged paths:", //
						"", //
						"\tboth modified:      unmerged", //
						"", //
						"Untracked files:", //
						"", //
						"\tuntracked", //
				}, execute("git status")); //
