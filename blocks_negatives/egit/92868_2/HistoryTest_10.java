		Git git = new Git(thisGit);

		createFile("Project-1/A.txt",A.txt - first version\n);
		createFile("Project-1/B.txt",B.txt - first version\n);
		git.add().addFilepattern("Project-1/A.txt").addFilepattern("Project-1/B.txt").call();
		git.commit().setAuthor(jauthor).setCommitter(jcommitter)
				.setMessage("Foo\n\nMessage").call();

		createFile("Project-1/B.txt",B.txt - second version\n);
		git.add().addFilepattern("Project-1/B.txt").call();
		git.commit().setAuthor(jauthor).setCommitter(jcommitter)
				.setMessage("Modified").call();
