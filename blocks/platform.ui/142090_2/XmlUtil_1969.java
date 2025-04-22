	public static IMemento read(File toRead) throws WorkbenchException {
		FileInputStream input;
		try {
			input = new FileInputStream(toRead);
			return read(input);
		} catch (FileNotFoundException e) {
			throw new WorkbenchException(new Status(IStatus.ERROR,
					TestPlugin.getDefault().getBundle().getSymbolicName(),
					IStatus.OK, null, e));
		}
	}
