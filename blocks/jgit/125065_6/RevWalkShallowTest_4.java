		StringBuilder builder = new StringBuilder();
		for (ObjectId commit : shallowCommits) {
			builder.append(commit.getName() + "\n");
		}
		JGitTestUtil.write(new File(db.getDirectory()
				builder.toString());
