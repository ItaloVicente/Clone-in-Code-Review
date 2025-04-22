
package org.eclipse.ui.examples.contributions.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.services.IDisposable;
import org.eclipse.ui.services.IServiceLocator;
import org.eclipse.ui.services.ISourceProviderService;

public class PersonService implements IPersonService, IDisposable {

	private static final int ME = 1114;
	private Map people = new TreeMap();
	private IServiceLocator serviceLocator;
	private ListenerList listeners = new ListenerList(ListenerList.IDENTITY);

	public PersonService(IServiceLocator locator) {
		serviceLocator = locator;
		serviceLocator.hasService(IHandlerService.class);
		fillModel();
	}

	private static final String[] datafill = {
			"Webster", "Paul", "Doe", "John", "Doe", "Jane", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
			"Public", "John", "Public", "Jane" }; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$

	private void fillModel() {
		int i = ME;
		for (int j = 0; j < datafill.length; j += 2) {
			Integer iid = new Integer(i++);
			Person p = new Person(iid.intValue(), datafill[j], datafill[j + 1]);
			if (p.getId() == ME) {
				p.setAdminRights(true);
			}
			people.put(iid, p);
		}
	}

	public void addPersonChangeListener(IPropertyChangeListener listener) {
		listeners.add(listener);
	}

	public Collection getPeople() {
		return Collections.unmodifiableCollection(people.values());
	}

	public Person getPerson(int id) {
		Person p = (Person) people.get(new Integer(id));
		if (p == null) {
			return null;
		}
		return p.copy();
	}

	public void removePersonChangeListener(IPropertyChangeListener listener) {
		listeners.remove(listener);
	}

	public void updatePerson(Person person) {
		Assert.isNotNull(person);
		Person p = (Person) people.get(new Integer(person.getId()));
		if (p == null) {
			Assert.isNotNull(p, "Must update a real person"); //$NON-NLS-1$
		}
		if (person.equals(p)) {
			return;
		}
		Person oldVal = p.copy();
		p.setGivenname(person.getGivenname());
		p.setSurname(person.getSurname());
		firePersonChange(PROP_CHANGE, oldVal, person);
	}

	private void firePersonChange(String property, Person oldVal, Person person) {
		if (listeners.isEmpty()) {
			return;
		}
		PropertyChangeEvent event = new PropertyChangeEvent(this, property,
				oldVal, person);
		Object[] array = listeners.getListeners();
		for (int i = 0; i < array.length; i++) {
			((IPropertyChangeListener) array[i]).propertyChange(event);
		}
	}

	public Person createPerson(int id) {
		Integer iid = new Integer(id);
		if (people.containsKey(iid)) {
			return null;
		}
		Person person = new Person(id, "surname", "givenname"); //$NON-NLS-1$//$NON-NLS-2$
		people.put(iid, person);
		firePersonChange(PROP_ADD, null, person);
		return person;
	}

	public void dispose() {
		listeners.clear();
		serviceLocator = null;
	}

	public void login(Person person) {
		ISourceProviderService sources = (ISourceProviderService) serviceLocator
				.getService(ISourceProviderService.class);
		UserSourceProvider userProvider = (UserSourceProvider) sources
				.getSourceProvider(UserSourceProvider.USER);
		userProvider.login(person);
	}
}
