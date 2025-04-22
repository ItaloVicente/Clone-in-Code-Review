	private static Map<PackFile
	private static boolean DEBUG_OPEN_PACKS = Boolean.getBoolean("jgit.debug-open-packs");

	public static void outputOpenPacks() {
		for (PackFile pack : openPackToStackTrace.keySet()) {
			System.err.println(pack + " " + pack.packFile);
			openPackToStackTrace.get(pack).printStackTrace();
		}
	}

