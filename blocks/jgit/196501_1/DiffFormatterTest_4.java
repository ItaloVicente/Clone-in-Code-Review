				"index " + a + ".." + b + " " + REGULAR_FILE + "\n";
	}

	private static String makeDiffHeaderAsText(String pathA
										 ObjectId aId
										 ObjectId bId) {
		String a = aId.abbreviate(7).name();
		String b = bId.abbreviate(7).name();
				"index " + a + ".." + b + " " + REGULAR_FILE + "\n";
	}

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
				"new file mode " + REGULAR_FILE + "\n" +
				"index " + a + ".." + b + "\n";
