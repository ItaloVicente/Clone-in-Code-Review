	private final GpgFormat keyFormat;

	private final String signingKey;

	private final String program;

	private final boolean signCommits;

	private final boolean signAllTags;

	private final boolean forceAnnotated;

	public GpgConfig(String keySpec
		keyFormat = format;
		signingKey = keySpec;
		program = gpgProgram;
		signCommits = true;
		signAllTags = false;
		forceAnnotated = false;
	}
