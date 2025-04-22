	private static class LfsRequest {
		String operation;
		List<LfsObject> objects;
	}

	private final LargeFileRepository repository;

	private Gson gson;

	/**
	 * Constructs a LFS protocol handler.
	 * <p>
	 * Use this constructor if the repository instance can be cached and doesn't
	 * change at runtime. Otherwise use {@link #LfsProtocolServlet()} and
	 * override {@link #getLargeFileRepository()} to provide the repository
	 * instance per-request at runtime.
	 *
	 * @param repository
	 *            the large file repository storing large files
	 */
	public LfsProtocolServlet(LargeFileRepository repository) {
		this.repository = repository;
		GsonBuilder gb = new GsonBuilder()
				.setFieldNamingPolicy(
						FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.setPrettyPrinting().disableHtmlEscaping();
		gson = gb.create();
	}

	/**
	 * Constructs a LFS protocol handler. To be used by subclasses which need to
	 * override the {@link #getLargeFileRepository} method.
	 */
	protected LfsProtocolServlet() {
	  this.repository = null;
	}
