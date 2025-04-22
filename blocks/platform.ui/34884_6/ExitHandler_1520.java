
package org.eclipse.e4.demo.contacts.handlers;

import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.demo.contacts.model.Contact;
import org.eclipse.e4.demo.contacts.model.ContactsRepositoryFactory;
import org.eclipse.e4.ui.model.application.ui.MContext;
import org.eclipse.e4.ui.services.IServiceConstants;

public class DeleteContactHandler {
	@CanExecute
	boolean canExecute(@Named(IServiceConstants.ACTIVE_PART) MContext context) {
		if (context == null)
			return false;
		Contact contact = (Contact) context.getContext().get(
				IServiceConstants.ACTIVE_SELECTION);
		return contact != null;
	}

	@Execute
	void execute(@Named(IServiceConstants.ACTIVE_PART) MContext context) {
		Contact contact = (Contact) context.getContext().get(
				IServiceConstants.ACTIVE_SELECTION);
		ContactsRepositoryFactory.getContactsRepository()
				.removeContact(contact);
	}
}
