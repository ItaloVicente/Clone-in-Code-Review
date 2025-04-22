/*******************************************************************************
 * Copyright (c) 2009 Siemens AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Kai Tödter - initial implementation
 ******************************************************************************/

package org.eclipse.e4.demo.contacts.model;

import org.eclipse.e4.demo.contacts.model.internal.VCardContactsRepository;

public class ContactsRepositoryFactory {

	private static final IContactsRepository CONTACTS_REPOSITORY = new VCardContactsRepository();

	public static IContactsRepository getContactsRepository() {
		return CONTACTS_REPOSITORY;
	}
}
