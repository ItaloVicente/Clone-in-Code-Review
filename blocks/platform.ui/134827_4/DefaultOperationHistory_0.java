
package org.eclipse.core.commands.internal.util;

public final class Tracing {

	public static final String SEPARATOR = " >>> "; //$NON-NLS-1$

	public static final void printTrace(final String component,
			final String message) {
		StringBuilder buffer = new StringBuilder();
		if (component != null) {
			buffer.append(component);
		}
		if ((component != null) && (message != null)) {
			buffer.append(SEPARATOR);
		}
		if (message != null) {
			buffer.append(message);
		}
		System.out.println(buffer.toString());
	}

	private Tracing() {
	}
}
