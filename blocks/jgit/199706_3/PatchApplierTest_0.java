		protected void initPreImage(String aName) throws Exception {
			File f = new File(db.getWorkTree()
			preImage = IO
					.readWholeStream(getTestResource(aName + "_PreImage")
					.array();
			try (Git git = new Git(db)) {
				Files.write(f.toPath()
				git.add().addFilepattern(aName).call();
			}
		}

		protected String initPostImage(String aName) throws Exception {
			postImage = IO
					.readWholeStream(getTestResource(aName + "_PostImage")
					.array();
			return new String(postImage
