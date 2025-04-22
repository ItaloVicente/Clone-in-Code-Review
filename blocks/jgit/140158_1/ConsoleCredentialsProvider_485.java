
package org.eclipse.jgit.console;

import java.io.Console;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.text.MessageFormat;

import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.util.CachedAuthenticator;

public class ConsoleAuthenticator extends CachedAuthenticator {
	public static void install() {
		final ConsoleAuthenticator c = new ConsoleAuthenticator();
		if (c.cons == null)
			throw new NoClassDefFoundError(
					CLIText.get().noSystemConsoleAvailable);
		Authenticator.setDefault(c);
	}

	private final Console cons = System.console();

	@Override
	protected PasswordAuthentication promptPasswordAuthentication() {
		final String realm = formatRealm();
		String username = cons.readLine(MessageFormat.format(
				CLIText.get().usernameFor + " "
		if (username == null || username.isEmpty()) {
			return null;
		}
		if (password == null) {
			password = new char[0];
		}
		return new PasswordAuthentication(username
	}

	private String formatRealm() {
		final StringBuilder realm = new StringBuilder();
		if (getRequestorType() == RequestorType.PROXY) {
			realm.append(getRequestorType());
			realm.append(getRequestingHost());
			if (getRequestingPort() > 0) {
				realm.append(getRequestingPort());
			}
		} else {
			realm.append(getRequestingURL());
		}
		return realm.toString();
	}
}
