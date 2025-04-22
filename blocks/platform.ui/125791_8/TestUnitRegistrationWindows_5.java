package org.eclipse.urischeme.internal.registration;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Assert;

public class RegistryWriterMock implements IRegistryWriter {

	private String key_software_classes;
	private String key_shell;
	private String key_open;
	private String key_command;
	String launcherPath;
	private static final String ATTRIBUTE_DEFAULT = null;
	private static final String ATTRIBUTE_EXECUTABLE = "Executable"; //$NON-NLS-1$
	private static final String ATTRIBUTE_PROTOCOL_MARKER = "URL Protocol"; //$NON-NLS-1$
	Map<String, Map<NameAndTypeMock, String>> registryMock;
	NameAndTypeMock[] nameAndTypeArray;

	public RegistryWriterMock() {
		this(System.getProperty("eclipse.launcher"), //$NON-NLS-1$
				System.getProperty("eclipse.home.location")); //$NON-NLS-1$
		registryMock = new HashMap<>();
		nameAndTypeArray = new NameAndTypeMock[4];
		setKeyValuePairs();
	}

	public RegistryWriterMock(String launcher, String homeLocation) {
		Assert.isNotNull(launcher, "launcher must not be null"); //$NON-NLS-1$
		Assert.isNotNull(homeLocation, "home location must not be null"); //$NON-NLS-1$
		setLauncherPath(launcher);
	}

	@Override
	public void addScheme(String scheme) throws IllegalArgumentException {
		changeKeys(scheme);
		Util.assertUriSchemeIsLegal(scheme);
		Map<NameAndTypeMock, String> registryEntries = new HashMap<>();
		registryEntries.put(nameAndTypeArray[0], "");
		registryEntries.put(nameAndTypeArray[1], "URL:" + scheme);
		registryEntries.put(nameAndTypeArray[2], getLauncherPath());
		String openCommand = IRegistryWriter.quote(getLauncherPath()) + " " + IRegistryWriter.quote("%1"); //$NON-NLS-1$ //$NON-NLS-2$
		registryEntries.put(nameAndTypeArray[3], openCommand);
		registryMock.put(scheme, registryEntries);
	}

	@Override
	public void removeScheme(String scheme) throws IllegalArgumentException {
		if (getRegisteredHandlerPath(scheme) == null) {
			return;
		}
		Util.assertUriSchemeIsLegal(scheme);
		Map<NameAndTypeMock, String> registryEntriesOfScheme = registryMock.get(scheme);
		for (int i = 0; i < nameAndTypeArray.length; i++) {
			registryEntriesOfScheme.remove(nameAndTypeArray[i]);
		}
	}

	@Override
	public String getRegisteredHandlerPath(String scheme) {
		changeKeys(scheme);
		Map<NameAndTypeMock, String> registryEntriesOfScheme = registryMock.get(scheme);
		if (null == registryEntriesOfScheme) {
			return null;
		}
		String marker = registryEntriesOfScheme.get(nameAndTypeArray[0]);
		if (null == marker) {
			return null;
		}
		String command = registryEntriesOfScheme.get(nameAndTypeArray[1]);
		if (null == command) {
			return null;
		}
		String exec = registryEntriesOfScheme.get(nameAndTypeArray[2]);
		if (exec == null) {
			return null;
		}
		return registryEntriesOfScheme.get(nameAndTypeArray[2]);
	}

	void changeKeys(String scheme) {
		key_software_classes = "Software\\Classes\\"; //$NON-NLS-1$
		key_software_classes += scheme;
		key_shell = key_software_classes + "\\shell"; //$NON-NLS-1$
		key_open = key_shell + "\\open"; //$NON-NLS-1$
		key_command = key_open + "\\command"; //$NON-NLS-1$
	}

	String getLauncherPath() {
		return launcherPath;
	}

	void setLauncherPath(String launcherPath) {
		this.launcherPath = launcherPath;
	}

	void setKeyValuePairs() {
		nameAndTypeArray[0] = new NameAndTypeMock(key_software_classes, ATTRIBUTE_PROTOCOL_MARKER);
		nameAndTypeArray[1] = new NameAndTypeMock(key_software_classes, ATTRIBUTE_DEFAULT);
		nameAndTypeArray[2] = new NameAndTypeMock(key_command, ATTRIBUTE_EXECUTABLE);
		nameAndTypeArray[3] = new NameAndTypeMock(key_command, ATTRIBUTE_DEFAULT);
	}

	private static class NameAndTypeMock {

		String name;
		String type;

		NameAndTypeMock(String attribute, String value) {
			this.name = attribute;
			this.type = value;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof NameAndTypeMock)) {
				return false;
			}
			NameAndTypeMock nameAndTypeMock = (NameAndTypeMock) obj;
			if ((this.name == null) ? (nameAndTypeMock.name != null) : !this.name.equals(nameAndTypeMock.name)) {
				return false;
			}
			if ((this.type == null) ? (nameAndTypeMock.type != null) : !this.type.equals(nameAndTypeMock.type)) {
				return false;
			}
			return true;
		}

		@Override
		public int hashCode() {
			int hash = 17;
			hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
			hash = 53 * hash + (this.type != null ? this.type.hashCode() : 0);
			return hash;
		}
	}

}
