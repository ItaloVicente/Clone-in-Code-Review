
package org.eclipse.egit.core.op;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import org.eclipse.egit.core.internal.trace.GitTraceLocation;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.osgi.util.NLS;

public class OperationLogger {
	private final String startMessage;

	private final String endMessage;

	private final String errorMessage;

	private final String[] parameter;

	public OperationLogger(String startMessage, String endMessage,
			String errorMessage, String[] parameter) {
		this.startMessage = startMessage;
		this.endMessage = endMessage;
		this.errorMessage = errorMessage;
		this.parameter = parameter;
	}

	public void logStart() {
		if (GitTraceLocation.OPTRACE.isActive()) {
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.OPTRACE.getLocation(),
					NLS.bind(startMessage, parameter));
		}
	}

	public void logEnd() {
		if (GitTraceLocation.OPTRACE.isActive()) {
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.OPTRACE.getLocation(),
					NLS.bind(endMessage, parameter));
		}
	}

	public void logEnd(String[] extraParameter) {
		if (GitTraceLocation.OPTRACE.isActive()) {
			String[] newParameter = Stream
					.concat(Arrays.stream(this.parameter),
							Arrays.stream(extraParameter))
					.toArray(String[]::new);
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.OPTRACE.getLocation(),
					NLS.bind(endMessage, newParameter));
		}
	}

	public void logError(Exception e) {
		if (GitTraceLocation.OPTRACE.isActive()) {
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.OPTRACE.getLocation(),
					NLS.bind(errorMessage, parameter) + ", " + e.getMessage()); //$NON-NLS-1$
		}
	}

	public static String getBranch(final Repository repo) {
		try {
			return repo.getBranch();
		} catch (IOException e) {
			return ""; //$NON-NLS-1$
		}
	}

	public static String getPath(final Repository repo) {
		if (repo.getDirectory() == null) {
			return ""; //$NON-NLS-1$
		} else {
			return repo.getDirectory().getAbsolutePath();
		}
	}
}
