	private void createLargeTestContent(Git git) throws IOException
			NoFilepatternException
			UnmergedPathsException
			WrongRepositoryStateException
		StringBuilder largeContent = new StringBuilder();
		Random r = new Random();
		for (int i = 0; i < 2000; i++) {
			for (int j = 0; j < 80; j++) {
				largeContent.append((char)(r.nextInt(26) + 'a'));
			}
			largeContent.append("\n");
		}
		writeTrashFile("large_file.txt"
		git.add().addFilepattern("large_file.txt").call();
		git.commit().setMessage("create file").call();
	}

