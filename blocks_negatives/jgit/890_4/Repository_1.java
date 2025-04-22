public class Repository {
	private final AtomicInteger useCnt = new AtomicInteger(1);

	private final File gitDir;

	private final FS fs;

	private final FileBasedConfig userConfig;

	private final RepositoryConfig config;

	private final RefDatabase refs;

	private final ObjectDirectory objectDatabase;

	private GitIndex index;

