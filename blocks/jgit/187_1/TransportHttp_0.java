	class HttpPushConnection extends BaseConnection implements PushConnection {

		@Override
		public void close() {
		}

		public void push(ProgressMonitor monitor
				Map<String
				throws TransportException {
			for (Entry<String
				RemoteRefUpdate refUpdate = entry.getValue();
				File workDir = local.getWorkDir();

				String srcRef = refUpdate.getSrcRef();
				String dstRef = refUpdate.getRemoteName();

				String repository = baseUrl.toString();

				String command = "git push "+ repository +" +" + srcRef + ":" + dstRef;
				execute(command
			}
		}

		private void execute(String command
				throws TransportException {
			System.out.println("Executing command: " + command);

			try {
				Process proc = Runtime.getRuntime().exec(command
				printResult(proc);
			} catch (IOException e) {
				throw new TransportException("Error executing git push"
			}
		}

		private void printResult(Process proc) throws IOException {
			BufferedReader output = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader error = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

			String stdOut = null
			while (((stdOut = output.readLine()) != null) || ((stdErr = error.readLine()) != null)) {
				if (stdOut != null)
					System.out.println(stdOut);
				if (stdErr != null)
					System.out.println(stdErr);
			}
		}

	}

