package org.eclipse.jgit.mailmap;

import org.eclipse.jgit.lib.PersonIdent;

public class MailmapEntry {

	private final String oldName;
	private final String oldEmail;
	private final String newName;
	private final String newEmail;

	MailmapEntry(String oldName
			String newEmail) {
		this.oldName = oldName;
		this.newName = newName;
		this.oldEmail = oldEmail;
		this.newEmail = newEmail;
	}

	public boolean matches(PersonIdent ident) {
		return oldEmail.equals(ident.getEmailAddress())
				&& (oldName == null || oldName.equals(ident.getName()));
	}

	public PersonIdent map(PersonIdent original) {
		String name = newName == null ? original.getName() : newName;
		String email = newEmail == null ? original.getEmailAddress() : newEmail;
		return new PersonIdent(name
				original.getTimeZone());
	}
}
