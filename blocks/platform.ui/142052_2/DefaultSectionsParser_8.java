	protected String getText(IFile file) {
		try {
			InputStream in = file.getContents();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int read = in.read(buf);
			while (read > 0) {
				out.write(buf, 0, read);
				read = in.read(buf);
			}
			return out.toString();
		} catch (CoreException e) {
		} catch (IOException e) {
		}
		return ""; //$NON-NLS-1$
	}
