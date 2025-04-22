	private void rememberHttpHost(CloneOperation op, URIish uri) {
		String scheme = uri.getScheme();
		if (scheme != null && scheme.toLowerCase().startsWith("http")) { //$NON-NLS-1$
			String host = uri.getHost();
			if (host != null) {
				op.addPostCloneTask(new RememberHostTask(host));
			}
		}
	}

