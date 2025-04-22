
package org.eclipse.egit.ui.internal.push;

import java.util.Collection;

public interface IPersonProvider {

	public class Person {
		private final String login;

		private final String name;

		public Person(String login, String name) {
			if (login == null) {
				throw new IllegalArgumentException("Login cannot be null"); //$NON-NLS-1$
			}

			this.login = login;
			this.name = name;
		}

		public String getLogin() {
			return login;
		}

		public String getName() {
			return name;
		}

	}

	Collection<Person> getPeople();
}
