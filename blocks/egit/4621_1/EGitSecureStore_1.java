		if (uri.getPort() == -1) {
			String s = uri.getScheme();
			if (s.equals("http")) //$NON-NLS-1$
				storedURI = storedURI.setPort(80);
			else if (s.equals("https")) //$NON-NLS-1$
				storedURI = storedURI.setPort(443);
			else if (s.equals("ssh") || s.equals("sftp")) //$NON-NLS-1$ //$NON-NLS-2$
				storedURI = storedURI.setPort(22);
			else if (s.equals("ftp")) //$NON-NLS-1$
				storedURI = storedURI.setPort(21);
		}
