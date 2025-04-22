
package org.eclipse.jgit.transport;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Repository;

public abstract class TransportProtocol {
	public static enum URIishField {
		USER
		PASS
		HOST
		PORT
		PATH
	}

	public abstract String getName();

	public Set<String> getSchemes() {
		return Collections.emptySet();
	}

	public Set<URIishField> getRequiredFields() {
		return Collections.unmodifiableSet(EnumSet.of(URIishField.PATH));
	}

	public Set<URIishField> getOptionalFields() {
		return Collections.emptySet();
	}

	public int getDefaultPort() {
		return -1;
	}

	public boolean canHandle(URIish uri) {
		return canHandle(uri
	}

	public boolean canHandle(URIish uri
		if (!getSchemes().isEmpty() && !getSchemes().contains(uri.getScheme()))
			return false;

		for (URIishField field : getRequiredFields()) {
			switch (field) {
			case USER:
				if (uri.getUser() == null || uri.getUser().length() == 0)
					return false;
				break;

			case PASS:
				if (uri.getPass() == null || uri.getPass().length() == 0)
					return false;
				break;

			case HOST:
				if (uri.getHost() == null || uri.getHost().length() == 0)
					return false;
				break;

			case PORT:
				if (uri.getPort() <= 0)
					return false;
				break;

			case PATH:
				if (uri.getPath() == null || uri.getPath().length() == 0)
					return false;
				break;

			default:
				return false;
			}
		}

		Set<URIishField> canHave = EnumSet.copyOf(getRequiredFields());
		canHave.addAll(getOptionalFields());

		if (uri.getUser() != null && !canHave.contains(URIishField.USER))
			return false;
		if (uri.getPass() != null && !canHave.contains(URIishField.PASS))
			return false;
		if (uri.getHost() != null && !canHave.contains(URIishField.HOST))
			return false;
		if (uri.getPort() > 0 && !canHave.contains(URIishField.PORT))
			return false;
		if (uri.getPath() != null && !canHave.contains(URIishField.PATH))
			return false;

		return true;
	}

	public abstract Transport open(URIish uri
			String remoteName)
			throws NotSupportedException

	public Transport open(URIish uri)
			throws NotSupportedException
		throw new NotSupportedException(JGitText
				.get().transportNeedsRepository);
	}
}
