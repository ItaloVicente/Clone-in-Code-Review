
package org.eclipse.ui.examples.contributions.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

public class UserSourceProvider extends AbstractSourceProvider {
	public static final String USER = "org.eclipse.ui.examples.contributions.user"; //$NON-NLS-1$
	private static final String[] PROVIDED_SOURCE_NAMES = new String[] { USER };
	private static final Object GUEST = new Object();

	private Person user = null;

	public void dispose() {
		user = null;
	}

	public Map getCurrentState() {
		Map m = new HashMap();
		m.put(USER, getCurrentUser());
		return m;
	}

	private Object getCurrentUser() {
		return user == null ? GUEST : user;
	}

	public void login(Person person) {
		user = person;
		fireSourceChanged(ISources.ACTIVE_SITE << 1, USER, getCurrentUser());
	}

	public String[] getProvidedSourceNames() {
		return PROVIDED_SOURCE_NAMES;
	}
}
