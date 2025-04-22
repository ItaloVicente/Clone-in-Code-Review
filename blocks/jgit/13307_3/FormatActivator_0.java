	private static final List<String> myFormats = new ArrayList<String>();

	private static final void register(String name
		myFormats.add(name);
		ArchiveCommand.registerFormat(name
	}

	public static void start() {
		register("tar"
		register("zip"
	}

	public static void stop() {
		for (String name : myFormats) {
			ArchiveCommand.unregisterFormat(name);
		}
		myFormats.clear();
	}

