		refsDir.mkdir();
		logsDir.mkdir();
		logsRefsDir.mkdir();

		new File(refsDir, R_HEADS.substring(R_REFS.length())).mkdir();
		new File(refsDir, R_TAGS.substring(R_REFS.length())).mkdir();
		new File(logsRefsDir, R_HEADS.substring(R_REFS.length())).mkdir();
