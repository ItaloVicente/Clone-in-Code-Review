package org.eclipse.egit.core.internal.trace;

public interface DebugTrace {

	public void trace(String location, String message);
	public void trace(String location, String message, Throwable error);

}
