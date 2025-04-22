		} else if (SSH.equals(s)) {
			if (u.getPort() == GERRIT_SSHD_DEFAULT_PORT) {
				return true;
			} else if (u.getPort() < 0 || u.getUser() == null
					|| getCredentialsProvider() == null) {
				return false;
			}
			URIish sshUri = u.setPath(""); //$NON-NLS-1$
			try {
				String result = runSshCommand(sshUri, getCredentialsProvider(),
						repo.getFS(), GERRIT_SSHD_VERSION_API);
				return result != null
						&& GERRIT_SSHD_REPLY.matcher(result).matches();
			} catch (IOException e) {
				return false;
			}
