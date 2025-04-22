		execute("git archive " +
			shellQuote("--output=" + archiveWithDot.getAbsolutePath()) + " " +
			"HEAD");
		execute("git archive " +
			shellQuote("--output=" + archiveNoDot.getAbsolutePath()) + " " +
			"HEAD");
