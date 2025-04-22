				"index " + a + ".." + b + " " + REGULAR_FILE + "\n";
	}

	private static String makeDiffHeaderAsText(String pathA
			ObjectId aId
			ObjectId bId) {
		String a = aId.abbreviate(7).name();
		String b = bId.abbreviate(7).name();
