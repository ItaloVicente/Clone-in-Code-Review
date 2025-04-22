	private final String command;

	private final SectionParser<ServiceConfig> configKey;

	private boolean enabled;

	private boolean overridable;

	DaemonService(final String cmdName
		command = cmdName.startsWith("git-") ? cmdName : "git-" + cmdName;
		configKey = cfg -> new ServiceConfig(DaemonService.this
		overridable = true;
	}

	private static class ServiceConfig {

		final boolean enabled;

		ServiceConfig(final DaemonService service
			enabled = cfg.getBoolean("daemon"
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean on) {
		enabled = on;
	}

	public boolean isOverridable() {
		return overridable;
	}

	public void setOverridable(final boolean on) {
		overridable = on;
	}

	public String getCommandName() {
		return command;
	}

	public boolean handles(final String commandLine) {
		return command.length() + 1 < commandLine.length() && commandLine.charAt(command.length()) == ' '
				&& commandLine.startsWith(command);
	}

	void execute(final org.eclipse.jgit.niofs.internal.daemon.git.DaemonClient client
			throws IOException
		final String name = commandLine.substring(command.length() + 1);
		Repository db;
		try {
			db = client.getDaemon().openRepository(client
		} catch (ServiceMayNotContinueException e) {
			PacketLineOut pktOut = new PacketLineOut(client.getOutputStream());
			pktOut.writeString("ERR " + e.getMessage() + "\n");
			db = null;
		}
		if (db == null) {
			return;
		}
		try {
			if (isEnabledFor(db)) {
				execute(client
			}
		} finally {
			db.close();
		}
	}

	private boolean isEnabledFor(final Repository db) {
		if (isOverridable()) {
			return db.getConfig().get(configKey).enabled;
		}
		return isEnabled();
	}

	abstract void execute(org.eclipse.jgit.niofs.internal.daemon.git.DaemonClient client
			throws IOException
