	private static boolean useExtConnection() {
		return SystemReader.getInstance().getenv("GIT_SSH") != null;
	}

	private class ExtConnection extends Connection {
		private Process proc;

		private int exitStatus;

		@Override
		void exec(String commandName) throws TransportException {
			String ssh = SystemReader.getInstance().getenv("GIT_SSH");
			boolean putty = ssh.toLowerCase().contains("plink");

			List<String> args = new ArrayList<String>();
			args.add(ssh);
			if (putty)
				args.add("--batch");
			if (0 < getURI().getPort()) {
				args.add(putty ? "-P" : "-p");
				args.add(String.valueOf(getURI().getPort()));
			}
			if (getURI().getUser() != null)
				args.add(getURI().getUser() + "@" + getURI().getHost());
			else
				args.add(getURI().getHost());
			args.add(commandFor(commandName));

			ProcessBuilder pb = new ProcessBuilder();
			pb.command(args);

			if (local.getDirectory() != null)
				pb.environment().put(Constants.GIT_DIR_KEY
						local.getDirectory().getPath());

			try {
				proc = pb.start();
			} catch (IOException err) {
				throw new TransportException(uri
			}
		}

		@Override
		void connect() throws TransportException {
		}

		@Override
		InputStream getInputStream() throws IOException {
			return proc.getInputStream();
		}

		@Override
		OutputStream getOutputStream() throws IOException {
			return proc.getOutputStream();
		}

		@Override
		InputStream getErrorStream() throws IOException {
			return proc.getErrorStream();
		}

		@Override
		int getExitStatus() {
			return exitStatus;
		}

		@Override
		void close() {
			if (proc != null) {
				try {
					try {
						exitStatus = proc.waitFor();
					} catch (InterruptedException e) {
					}
				} finally {
					proc = null;
				}
			}
		}
	}

