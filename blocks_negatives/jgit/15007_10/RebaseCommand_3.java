		BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(rebaseState.getFile(GIT_REBASE_TODO)),
				Constants.CHARACTER_ENCODING));
		fw.write("# Created by EGit: rebasing " + headId.name() + " onto "
				+ upstreamCommit.name());
		fw.newLine();
		try {
			StringBuilder sb = new StringBuilder();
			ObjectReader reader = walk.getObjectReader();
			for (RevCommit commit : cherryPickList) {
				sb.setLength(0);
				sb.append(Action.PICK.toToken());
				sb.append(reader.abbreviate(commit).name());
				sb.append(commit.getShortMessage());
				fw.write(sb.toString());
				fw.newLine();
			}
		} finally {
			fw.close();
		}
