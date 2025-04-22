package org.eclipse.egit.ui.internal.trace;

public interface DebugTrace {

	public void trace(String location, String message);
	public void trace(String location, String message, Throwable error);

	public void traceEntry(String location);

	public void traceEntry(String location, String message);

}
