		private int exitStatus;

		@Override
		void exec(String commandName) throws TransportException {
			String ssh = SystemReader.getInstance().getenv("GIT_SSH");
			boolean putty = ssh.toLowerCase().contains("plink");

			List<String> args = new ArrayList<String>();
