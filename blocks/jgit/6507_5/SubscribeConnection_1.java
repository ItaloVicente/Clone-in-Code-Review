
package org.eclipse.jgit.transport;

public class SubscribeCommand {
	public enum Command {
		SUBSCRIBE
		UNSUBSCRIBE
	}

	private Command command;

	private String spec;

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
}
