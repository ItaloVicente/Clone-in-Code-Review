		StringBuilder buf = new StringBuilder();
		if (isDirectory()) {
			buf.append("Folder(");//$NON-NLS-1$
		} else {
			buf.append("File(");//$NON-NLS-1$
		}
		buf.append(name).append(")");//$NON-NLS-1$
		if (!isDirectory()) {
			return buf.toString();
		}
		buf.append(" folders: ");//$NON-NLS-1$
		buf.append(folders);
		buf.append(" files: ");//$NON-NLS-1$
		buf.append(files);
		return buf.toString();
	}
