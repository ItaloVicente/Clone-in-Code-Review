
package org.eclipse.e4.demo.contacts.model;

import org.eclipse.core.databinding.observable.list.IObservableList;

public interface IContactsRepository {
	IObservableList getAllContacts();

	void addContact(Contact contact);

	void removeContact(Contact contact);
}
