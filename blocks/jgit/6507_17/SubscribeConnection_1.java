
package org.eclipse.jgit.transport;

import java.util.Arrays;

public class SubscribeCommand {
	public enum Command {
		SUBSCRIBE
		UNSUBSCRIBE
	}

	private final Command command;

	private final String spec;

	public SubscribeCommand(Command c
		command = c;
		spec = s;
	}

	public String getSpec() {
		return spec;
	}

	public Command getCommand() {
		return command;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof SubscribeCommand))
			return false;
		SubscribeCommand o = (SubscribeCommand) other;
		return o.command == command && o.spec.equals(spec);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(new Object[] { command
	}

	@Override
	public String toString() {
		return "SubscribeCommand[" + command + " " + spec + "]";
	}
}
