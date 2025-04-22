package org.eclipse.egit.core.internal.trace;

import com.jcraft.jsch.Logger;

public class JSchLogger implements Logger {

	@Override
	public boolean isEnabled(int level) {
		return GitTraceLocation.JSCH.isActive();
	}

	@Override
	public void log(int level, String message) {
		if (isEnabled(level)) {
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.JSCH.getLocation(),
					levelToString(level) + ": " + message); //$NON-NLS-1$
		}
	}

	private String levelToString(int level) {
		switch (level) {
		case 0:
			return "DEBUG"; //$NON-NLS-1$
		case 1:
			return "INFO"; //$NON-NLS-1$
		case 2:
			return "WARN"; //$NON-NLS-1$
		case 3:
			return "ERROR"; //$NON-NLS-1$
		case 4:
			return "FATAL"; //$NON-NLS-1$
		default:
			return "UNKNOWN(" + level + ')'; //$NON-NLS-1$
		}
	}
}
