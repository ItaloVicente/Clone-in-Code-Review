	public static IMemento read(URL toRead) throws WorkbenchException {
		try {
			return read(toRead.openStream());
		} catch (IOException e) {
			throw new WorkbenchException(new Status(IStatus.ERROR,
					TestPlugin.getDefault().getBundle().getSymbolicName(),
					IStatus.OK, null, e));
		}
	}
