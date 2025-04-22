		URIish storedURI = uri.setUser(null).setPass(null);
		if (uri.getScheme() != null && !"file".equals(uri.getScheme())) { //$NON-NLS-1$
			storedURI = storedURI.setPath(null);
			if (uri.getPort() == -1) {
				String s = uri.getScheme();
				if ("http".equals(s)) //$NON-NLS-1$
					storedURI = storedURI.setPort(80);
				else if ("https".equals(s)) //$NON-NLS-1$
					storedURI = storedURI.setPort(443);
				else if ("ssh".equals(s) || "sftp".equals(s)) //$NON-NLS-1$ //$NON-NLS-2$
					storedURI = storedURI.setPort(22);
				else if ("ftp".equals(s)) //$NON-NLS-1$
					storedURI = storedURI.setPort(21);
			}
