package org.eclipse.urischeme.internal.registration;

public class RegistryWriter implements IRegistryWriter {

	private String key_software_classes;
	private String key_shell;
	private String key_open;
	private String key_command;
	private static final String ATTRIBUTE_DEFAULT = null;
	private static final String ATTRIBUTE_EXECUTABLE = "Executable"; //$NON-NLS-1$
	private static final String ATTRIBUTE_PROTOCOL_MARKER = "URL Protocol"; //$NON-NLS-1$

	private IWinRegistry winRegistry = null;
	private IFileProvider fileProvider = null;

	public RegistryWriter() {
		this(new WinRegistry(), new FileProvider());
	}

	public RegistryWriter(IWinRegistry winRegistry, IFileProvider fileProvider) {
		this.winRegistry = winRegistry;
		this.fileProvider = fileProvider;
	}

	@Override
	public void addScheme(String scheme, String launcherPath) throws WinRegistryException {
		Util.assertUriSchemeIsLegal(scheme);
		getRegisteredHandlerPath(scheme);

		winRegistry.setValueForKey(key_software_classes, ATTRIBUTE_PROTOCOL_MARKER, ""); //$NON-NLS-1$
		winRegistry.setValueForKey(key_software_classes, ATTRIBUTE_DEFAULT, "URL:" + scheme); //$NON-NLS-1$
		winRegistry.setValueForKey(key_command, ATTRIBUTE_EXECUTABLE, launcherPath);
		String openCommand = IRegistryWriter.quote(launcherPath) + " " + IRegistryWriter.quote("%1"); //$NON-NLS-1$ //$NON-NLS-2$
		winRegistry.setValueForKey(key_command, ATTRIBUTE_DEFAULT, openCommand);
	}

	@Override
	public void removeScheme(String scheme) throws WinRegistryException {
		Util.assertUriSchemeIsLegal(scheme);
		if (getRegisteredHandlerPath(scheme) == null) {
			return;
		}
		winRegistry.deleteKey(key_command);
		winRegistry.deleteKey(key_open);
		winRegistry.deleteKey(key_shell);
		winRegistry.deleteKey(key_software_classes);
	}

	@Override
	public String getRegisteredHandlerPath(String scheme) throws WinRegistryException {
		changeKeys(scheme);
		String marker = winRegistry.getValueForKey(key_software_classes, ATTRIBUTE_PROTOCOL_MARKER);
		if (marker == null) {
			return null;
		}
		String command = winRegistry.getValueForKey(key_command, ATTRIBUTE_DEFAULT);
		if (command == null) {
			return null;
		}
		String exec = winRegistry.getValueForKey(key_command, ATTRIBUTE_EXECUTABLE);
		if (exec == null) {
			return null;
		}
		if (!fileProvider.fileExists(exec)) {
			return null;
		}
		return exec;
	}

	void changeKeys(String scheme) {
		key_software_classes = "Software\\Classes\\"; //$NON-NLS-1$
		key_software_classes += scheme;
		key_shell = key_software_classes + "\\shell"; //$NON-NLS-1$
		key_open = key_shell + "\\open"; //$NON-NLS-1$
		key_command = key_open + "\\command"; //$NON-NLS-1$
	}

}
