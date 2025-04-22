		Git git = new Git(repository1.getRepository());
		git.add().addFilepattern("file1.txt").call();

		git.commit().setMessage("first commit").call();
		git.tag().setName("tag").call();

		file = new File(workdir, "file2.txt");
		FileUtils.createNewFile(file);
		git.add().addFilepattern("file2.txt").call();
		git.commit().setMessage("second commit").call();
		git.branchCreate().setName("dev").call();

		file = new File(workdir, "file3.txt");
		FileUtils.createNewFile(file);
		git.add().addFilepattern("file3.txt").call();
		git.commit().setMessage("third commit").call();
