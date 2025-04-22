
	private void updateFields(final String text) {
		try {
			eventDepth++;
			if (eventDepth != 1)
				return;

			final URIish u = new URIish(text);
			safeSet(hostText, u.getHost());
			safeSet(pathText, u.getPath());
			safeSet(userText, u.getUser());
			safeSet(passText, u.getPass());

			if (u.getPort() > 0)
				portText.setText(Integer.toString(u.getPort()));
			else
				portText.setText(""); //$NON-NLS-1$

			if (isFile(u))
				scheme.select(S_FILE);
			else if (isSSH(u))
				scheme.select(S_SSH);
			else {
				for (int i = 0; i < DEFAULT_SCHEMES.length; i++) {
					if (DEFAULT_SCHEMES[i].equals(u.getScheme())) {
						scheme.select(i);
						break;
					}
				}
			}

			updateAuthGroup();
			uri = u;
		} catch (URISyntaxException err) {
			uri = new URIish();
			hostText.setText(""); //$NON-NLS-1$
			pathText.setText(""); //$NON-NLS-1$
			userText.setText(""); //$NON-NLS-1$
			passText.setText(""); //$NON-NLS-1$
			portText.setText(""); //$NON-NLS-1$
			scheme.select(0);
		} finally {
			eventDepth--;
		}
		checkPage();
	}
