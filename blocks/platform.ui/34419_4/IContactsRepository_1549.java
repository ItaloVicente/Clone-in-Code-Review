
package org.eclipse.e4.demo.contacts.model;

import org.eclipse.e4.demo.contacts.model.internal.VCardContactsRepository;

public class ContactsRepositoryFactory {

	private static final IContactsRepository CONTACTS_REPOSITORY = new VCardContactsRepository();

	public static IContactsRepository getContactsRepository() {
		return CONTACTS_REPOSITORY;
	}
}
