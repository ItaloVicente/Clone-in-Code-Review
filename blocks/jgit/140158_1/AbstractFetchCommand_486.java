
package org.eclipse.jgit.console;

import java.io.Console;

import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.transport.ChainingCredentialsProvider;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.NetRCCredentialsProvider;
import org.eclipse.jgit.transport.URIish;

public class ConsoleCredentialsProvider extends CredentialsProvider {
	public static void install() {
		final ConsoleCredentialsProvider c = new ConsoleCredentialsProvider();
		if (c.cons == null)
			throw new NoClassDefFoundError(
					CLIText.get().noSystemConsoleAvailable);
		CredentialsProvider cp = new ChainingCredentialsProvider(
				new NetRCCredentialsProvider()
		CredentialsProvider.setDefault(cp);
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
				ok = get((CredentialItem.StringType) item);

			else if (item instanceof CredentialItem.CharArrayType)
				ok = get((CredentialItem.CharArrayType) item);

			else if (item instanceof CredentialItem.YesNoType)
				ok = get((CredentialItem.YesNoType) item);

			else if (item instanceof CredentialItem.InformationalMessage)
				ok = get((CredentialItem.InformationalMessage) item);

			else
				throw new UnsupportedCredentialItem(uri
		}
		return ok;
	}

	private boolean get(CredentialItem.StringType item) {
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

	private boolean get(CredentialItem.CharArrayType item) {
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

	private boolean get(CredentialItem.InformationalMessage item) {
		cons.printf("%s\n"
		cons.flush();
		return true;
	}

	private boolean get(CredentialItem.YesNoType item) {
		String r = cons.readLine("%s [%s/%s]? "
				CLIText.get().answerYes
		if (r != null) {
			item.setValue(CLIText.get().answerYes.equalsIgnoreCase(r));
			return true;
		} else {
			return false;
		}
	}
}
