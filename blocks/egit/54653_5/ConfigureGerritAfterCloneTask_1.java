		} else if (SSH.equals(s)) {
			if (u.getPort() < 0 || u.getUser() == null
					|| credentialsProvider == null) {
				return false;
			}
			URIish sshUri = u.setPath(""); //$NON-NLS-1$
			try {
				String result = runSshCommand(sshUri, credentialsProvider,
						repo.getFS(), GERRIT_SSHD_VERSION_API);
				return result != null
						&& GERRIT_SSHD_REPLY.matcher(result).matches();
			} catch (IOException e) {
				return false;
			}
