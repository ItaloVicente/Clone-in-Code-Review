
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Config.SectionParser;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

public abstract class DaemonService {
	private final String command;

	private final SectionParser<ServiceConfig> configKey;

	private boolean enabled;

	private boolean overridable;

	DaemonService(String cmdName
		configKey = cfg -> new ServiceConfig(DaemonService.this
		overridable = true;
	}

	private static class ServiceConfig {
		final boolean enabled;

		ServiceConfig(final DaemonService service
				final String name) {
			enabled = cfg.getBoolean("daemon"
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean on) {
		enabled = on;
	}

	public boolean isOverridable() {
		return overridable;
	}

	public void setOverridable(boolean on) {
		overridable = on;
	}

	public String getCommandName() {
		return command;
	}

	public boolean handles(String commandLine) {
		return command.length() + 1 < commandLine.length()
				&& commandLine.charAt(command.length()) == ' '
				&& commandLine.startsWith(command);
	}

	void execute(DaemonClient client
			@Nullable Collection<String> extraParameters)
			throws IOException
			ServiceNotAuthorizedException {
		final String name = commandLine.substring(command.length() + 1);
		try (Repository db = client.getDaemon().openRepository(client
			if (isEnabledFor(db)) {
				execute(client
			}
		} catch (ServiceMayNotContinueException e) {
			PacketLineOut pktOut = new PacketLineOut(client.getOutputStream());
		}
	}

	private boolean isEnabledFor(Repository db) {
		if (isOverridable())
			return db.getConfig().get(configKey).enabled;
		return isEnabled();
	}

	abstract void execute(DaemonClient client
			@Nullable Collection<String> extraParameters)
			throws IOException
			ServiceNotAuthorizedException;
}
