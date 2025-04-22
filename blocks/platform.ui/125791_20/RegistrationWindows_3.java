package org.eclipse.urischeme.internal.registration;

public interface IWinRegistry {

	void setValueForKey(String key, String attribute, String value) throws WinRegistryException;

	String getValueForKey(String key, String attribute) throws WinRegistryException;

	void deleteKey(String key) throws WinRegistryException;

}
