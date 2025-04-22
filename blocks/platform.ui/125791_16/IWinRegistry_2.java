package org.eclipse.urischeme.internal.registration;

public interface IRegistryWriter {

	static String quote(String string) {
		return String.format("\"%s\"", string); //$NON-NLS-1$
	}

	void addScheme(String scheme) throws WinRegistryException;


	void removeScheme(String scheme) throws WinRegistryException;

	String getRegisteredHandlerPath(String scheme) throws WinRegistryException;

}
