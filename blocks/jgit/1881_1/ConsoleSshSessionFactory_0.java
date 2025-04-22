
package org.eclipse.jgit.console;

import java.io.Console;

import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.URIish;

public class ConsoleCredentialsProvider extends CredentialsProvider {
	public static void install() {
		final ConsoleCredentialsProvider c = new ConsoleCredentialsProvider();
		if (c.cons == null)
			throw new NoClassDefFoundError(
					ConsoleText.get().noSystemConsoleAvailable);
		CredentialsProvider.setDefault(c);
	}

	private final Console cons = System.console();

	@Override
	public boolean isInteractive() {
		return true;
	}

	@Override
	public boolean supports(CredentialItem... items) {
		for (CredentialItem i : items) {
			if (i instanceof CredentialItem.StringType)
				continue;

			else if (i instanceof CredentialItem.CharArrayType)
				continue;

			else if (i instanceof CredentialItem.YesNoType)
				continue;

			else if (i instanceof CredentialItem.InformationalMessage)
				continue;

			else
				return false;
		}
		return true;
	}

	@Override
	public boolean get(URIish uri
			throws UnsupportedCredentialItem {
		boolean ok = true;
		for (int i = 0; i < items.length && ok; i++) {
			CredentialItem item = items[i];

			if (item instanceof CredentialItem.StringType)
				ok = get(uri

			else if (item instanceof CredentialItem.CharArrayType)
				ok = get(uri

			else if (item instanceof CredentialItem.YesNoType)
				ok = get(uri

			else if (item instanceof CredentialItem.InformationalMessage)
				ok = get(uri

			else
				throw new UnsupportedCredentialItem(uri
		}
		return ok;
	}

	private boolean get(URIish uri
		if (item.isValueSecure()) {
			char[] v = cons.readPassword("%s: "
			if (v != null) {
				item.setValue(new String(v));
				return true;
			} else {
				return false;
			}
		} else {
			String v = cons.readLine("%s: "
			if (v != null) {
				item.setValue(v);
				return true;
			} else {
				return false;
			}
		}
	}

	private boolean get(URIish uri
		if (item.isValueSecure()) {
			char[] v = cons.readPassword("%s: "
			if (v != null) {
				item.setValueNoCopy(v);
				return true;
			} else {
				return false;
			}
		} else {
			String v = cons.readLine("%s: "
			if (v != null) {
				item.setValueNoCopy(v.toCharArray());
				return true;
			} else {
				return false;
			}
		}
	}

	private boolean get(URIish uri
		cons.printf("%s\n"
		cons.flush();
		return true;
	}

	private boolean get(URIish uri
		String r = cons.readLine("%s [%s/%s]? "
				ConsoleText.get().answerYes
		if (r != null) {
			item.setValue(ConsoleText.get().answerYes.equalsIgnoreCase(r));
			return true;
		} else {
			return false;
		}
	}
}
