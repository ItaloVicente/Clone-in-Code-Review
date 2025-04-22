
package org.eclipse.jgit.console;

import java.io.Console;

import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.SshConfigSessionFactory;
import org.eclipse.jgit.transport.SshSessionFactory;

import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

public class ConsoleSshSessionFactory extends SshConfigSessionFactory {
	public static void install() {
		final ConsoleSshSessionFactory c = new ConsoleSshSessionFactory();
		if (c.cons == null)
			throw new NoClassDefFoundError("No System.console available");
		SshSessionFactory.setInstance(c);
	}

	private final Console cons = System.console();

	@Override
	protected void configure(final OpenSshConfig.Host hc
		if (!hc.isBatchMode())
			session.setUserInfo(new ConsoleUserInfo());
	}

	private class ConsoleUserInfo implements UserInfo
		private String passwd;

		private String passphrase;

		public void showMessage(final String msg) {
			cons.printf("%s\n"
			cons.flush();
		}

		public boolean promptYesNo(final String msg) {
			String r = cons.readLine("%s [y/n]? "
			return "y".equalsIgnoreCase(r);
		}

		public boolean promptPassword(final String msg) {
			passwd = null;
			char[] p = cons.readPassword("%s: "
			if (p != null) {
				passwd = new String(p);
				return true;
			}
			return false;
		}

		public boolean promptPassphrase(final String msg) {
			passphrase = null;
			char[] p = cons.readPassword("%s: "
			if (p != null) {
				passphrase = new String(p);
				return true;
			}
			return false;
		}

		public String getPassword() {
			return passwd;
		}

		public String getPassphrase() {
			return passphrase;
		}

		public String[] promptKeyboardInteractive(final String destination
				final String name
				final String[] prompt
			cons.printf("%s: %s\n"
			cons.printf("%s\n"
			final String[] response = new String[prompt.length];
			for (int i = 0; i < prompt.length; i++) {
				if (echo[i]) {
					response[i] = cons.readLine("%s: "
				} else {
					final char[] p = cons.readPassword("%s: "
					response[i] = p != null ? new String(p) : "";
				}
			}
			return response;
		}
	}
}
