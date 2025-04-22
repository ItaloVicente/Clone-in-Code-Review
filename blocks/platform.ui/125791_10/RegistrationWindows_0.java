package org.eclipse.urischeme.internal.registration;

public interface IRegistryWriter {

	static String quote(String string) {
		return String.format("\"%s\"", string); //$NON-NLS-1$
	}

	void addScheme(String scheme) throws IllegalArgumentException;


	void removeScheme(String scheme) throws IllegalArgumentException;

	String getRegisteredHandlerPath(String scheme);

}
