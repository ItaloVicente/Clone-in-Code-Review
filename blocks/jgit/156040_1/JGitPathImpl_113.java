public class JGitPathImpl extends AbstractPath<JGitFileSystem> implements SegmentedPath {

	private static final int BUFFER_SIZE = 8192;
	public final static String DEFAULT_REF_TREE = MASTER;

	private final ObjectId objectId;

	private JGitPathImpl(final JGitFileSystem fs
			final boolean isRoot
		super(fs
		this.objectId = id;
	}

	@Override
	protected RootInfo setupRoot(final JGitFileSystem fs
		final boolean isRooted = isRoot ? true : pathx.startsWith("/");

		final boolean isAbsolute;
		if (isRooted) {
			isAbsolute = true;
		} else {
			isAbsolute = false;
		}

		int lastOffset = isAbsolute ? 1 : 0;

		final boolean isFinalRoot;
		if (pathx.length() == 1 && lastOffset == 1) {
			isFinalRoot = true;
		} else {
			isFinalRoot = isRoot;
		}

		return new RootInfo(lastOffset
	}

	@Override
	protected String defaultDirectory() {
		return "/:";
	}

	@Override
	protected Path newRoot(final JGitFileSystem fs
		return new JGitPathImpl(fs
	}

	@Override
	protected Path newPath(final JGitFileSystem fs
			final boolean isNormalized) {
		return new JGitPathImpl(fs
	}

	public static JGitPathImpl create(final JGitFileSystem fs
			final boolean isRealPath) {
		return new JGitPathImpl(fs
	}

	public static JGitPathImpl create(final JGitFileSystem fs
			final boolean isRealPath) {
		return new JGitPathImpl(fs
	}

	public static JGitPathImpl createRoot(final JGitFileSystem fs
			final boolean isRealPath) {
		return new JGitPathImpl(fs
	}

	public static JGitPathImpl createFSDirect(final JGitFileSystem fs) {
		return new JGitPathImpl(fs
	}

	@Override
	public File toFile() {
		if (file == null) {
			synchronized (this) {
				if (isRegularFile()) {
					try {
						file = File.createTempFile("git"
						try (final InputStream in = getFileSystem().provider().newInputStream(this);
								final OutputStream out = new FileOutputStream(file)) {
							internalCopy(in
						}
					} catch (final Exception ex) {
						file = null;
					}
				} else {
					throw new UnsupportedOperationException();
				}
			}
		}
		return file;
	}

	private static String setupHost(final String host) {
		if (!host.contains("@")) {
			return DEFAULT_REF_TREE + "@" + host;
		}

		return host;
	}

	private static String setupPath(final String path) {
		if (path.isEmpty()) {
			return "/";
		}
		return path;
	}

	public String getRefTree() {
		return host.substring(0
	}

	public String getPath() {
		return new String(path);
	}

	public boolean isRegularFile() throws IllegalAccessError
		try {
			return getFileSystem().provider().readAttributes(this
		} catch (IOException ioe) {
		}
		return false;
	}

	private static long internalCopy(final InputStream in
		long read = 0L;
		byte[] buf = new byte[BUFFER_SIZE];
		int n;
		try {
			while ((n = in.read(buf)) > 0) {
				out.write(buf
				read += n;
			}
		} catch (java.io.IOException e) {
			throw new RuntimeException(e);
		}
		return read;
	}

	@Override
	public String getSegmentId() {
		return getRefTree();
	}
