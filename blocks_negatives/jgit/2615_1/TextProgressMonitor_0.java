/**
 * A simple progress reporter printing on stderr
 */
public class TextProgressMonitor implements ProgressMonitor {
	private boolean output;

	private long taskBeganAt;

	private String msg;
