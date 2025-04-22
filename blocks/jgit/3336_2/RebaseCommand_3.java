		if (!cherryPickList.isEmpty()) {
			Collections.reverse(cherryPickList);
			FileUtils.mkdir(rebaseDir);

			createFile(repo.getDirectory()
			createFile(rebaseDir
			createFile(rebaseDir
			createFile(rebaseDir
			createFile(rebaseDir
			BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(rebaseDir
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
