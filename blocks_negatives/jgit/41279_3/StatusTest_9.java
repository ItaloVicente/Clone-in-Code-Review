				}, execute("git status --porcelain")); //
		git.checkout().setName("master").call();
		writeTrashFile("unmerged", "changed in master branch");
		git.add().addFilepattern("unmerged").call();
		git.commit().setMessage("changed unmerged in master branch").call();
						"?? untracked", //
