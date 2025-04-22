		Collections.reverse(cherryPickList);
		FileUtils.mkdir(rebaseDir);

		createFile(repo.getDirectory(), Constants.ORIG_HEAD, headId.name());
		createFile(rebaseDir, REBASE_HEAD, headId.name());
		createFile(rebaseDir, HEAD_NAME, headName);
		createFile(rebaseDir, ONTO, upstreamCommit.name());
		createFile(rebaseDir, INTERACTIVE, "");
		BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(new File(rebaseDir, GIT_REBASE_TODO)),
				Constants.CHARACTER_ENCODING));
		fw.write("# Created by EGit: rebasing " + upstreamCommit.name()
				+ " onto " + headId.name());
		fw.newLine();
		try {
			StringBuilder sb = new StringBuilder();
			ObjectReader reader = walk.getObjectReader();
			for (RevCommit commit : cherryPickList) {
				sb.setLength(0);
				sb.append(Action.PICK.toToken());
				sb.append(" ");
				sb.append(reader.abbreviate(commit).name());
				sb.append(" ");
				sb.append(commit.getShortMessage());
				fw.write(sb.toString());
				fw.newLine();
			}
		} finally {
			fw.close();
		}
