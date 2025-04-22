public final class PackOutputStream extends OutputStream {
	private static final int BYTES_TO_WRITE_BEFORE_CANCEL_CHECK = 128 * 1024;

	private final ProgressMonitor writeMonitor;

	private final OutputStream out;
