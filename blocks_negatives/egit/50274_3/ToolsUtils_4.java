		try {
			Process p = Runtime.getRuntime().exec(cmdarray, envp);
			exitCode = p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
