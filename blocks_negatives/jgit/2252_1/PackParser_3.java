/** Indexes Git pack files for local use. */
public class IndexPack {
	/**
	 * Size of the internal stream buffer.
	 * <p>
	 * If callers are going to be supplying IndexPack a BufferedInputStream they
	 * should use this buffer size as the size of the buffer for that
	 * BufferedInputStream, and any other its may be wrapping. This way the
	 * buffers will cascade efficiently and only the IndexPack buffer will be
	 * receiving the bulk of the data stream.
	 */
	public static final int BUFFER_SIZE = 8192;

	/**
	 * Create an index pack instance to load a new pack into a repository.
	 * <p>
	 * The received pack data and generated index will be saved to temporary
	 * files within the repository's <code>objects</code> directory. To use the
	 * data contained within them call {@link #renameAndOpenPack()} once the
	 * indexing is complete.
	 *
	 * @param db
	 *            the repository that will receive the new pack.
	 * @param is
	 *            stream to read the pack data from. If the stream is buffered
	 *            use {@link #BUFFER_SIZE} as the buffer size for the stream.
	 * @return a new index pack instance.
	 * @throws IOException
	 *             a temporary file could not be created.
	 */
	public static IndexPack create(final Repository db, final InputStream is)
			throws IOException {
		final String suffix = ".pack";
		final File objdir = db.getObjectsDirectory();
		final File tmp = File.createTempFile("incoming_", suffix, objdir);
		final String n = tmp.getName();
		final File base;

		base = new File(objdir, n.substring(0, n.length() - suffix.length()));
		final IndexPack ip = new IndexPack(db, is, base);
		ip.setIndexVersion(db.getConfig().get(CoreConfig.KEY)
				.getPackIndexVersion());
		return ip;
	}
