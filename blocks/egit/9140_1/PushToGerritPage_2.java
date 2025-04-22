
package org.eclipse.egit.ui.internal.preferences;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.IPersonProvider;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;

public final class PreferenceStorePersonProvider implements IPersonProvider {

	public static final String ROOT_ELEMENT = "users"; //$NON-NLS-1$

	public static final String USER_ELEMENT = "user"; //$NON-NLS-1$

	public static final String USER_NAME_ELEMENT = "name"; //$NON-NLS-1$

	public static final String USER_LOGIN_ELEMENT = "login"; //$NON-NLS-1$

	private static final PreferenceStorePersonProvider INSTANCE = new PreferenceStorePersonProvider();

	public static PreferenceStorePersonProvider getInstance() {
		return INSTANCE;
	}

	private PreferenceStorePersonProvider() {
	}

	public Collection<Person> getPeople() {
		IPreferenceStore preferenceStore = Activator.getDefault()
				.getPreferenceStore();

		List<Person> result = new ArrayList<Person>();

		String usersRaw = preferenceStore
				.getString(GerritUsersPreferencePage.USERS_PREFERENCE_KEY);

		if (usersRaw != null && usersRaw.length() > 0) {
			try {
				XMLMemento memento = XMLMemento
						.createReadRoot(new StringReader(usersRaw));
				for (IMemento userMemento : memento.getChildren(USER_ELEMENT)) {
					result.add(new Person(userMemento
							.getString(USER_LOGIN_ELEMENT), userMemento
							.getString(USER_NAME_ELEMENT)));
				}
			} catch (WorkbenchException e) {
				Activator.handleError(
						UIText.PreferenceStorePersonProvider_ExceptionMessage,
						e, true);
			} catch (IllegalArgumentException e) {
				Activator.handleError(
						UIText.PreferenceStorePersonProvider_ExceptionMessage,
						e, false);
			}
		}

		return result;
	}
}
