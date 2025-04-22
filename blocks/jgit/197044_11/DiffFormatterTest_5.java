	private static String makeDiffHeaderAsBinary(String pathA
			ObjectId aId
			ObjectId bId) {
		String a = aId.abbreviate(40).name();
		String b = bId.abbreviate(40).name();
				"index " + a + ".." + b + " " + REGULAR_FILE + "\n";
	}

	private static String makeDiffADDHeaderAsBinary(String pathA
			ObjectId aId
			ObjectId bId) {
		String a = aId.abbreviate(40).name();
		String b = bId.abbreviate(40).name();
				"index " + a + ".." + b + "\n";
	}

