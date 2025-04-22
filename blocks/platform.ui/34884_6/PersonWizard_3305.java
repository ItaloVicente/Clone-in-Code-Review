
package org.eclipse.ui.examples.contributions.model;

import java.util.Collection;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.services.IDisposable;
import org.eclipse.ui.services.IServiceLocator;

public class PersonServiceSlave implements IPersonService, IDisposable {

	private IServiceLocator serviceLocator;
	private IPersonService parentService;
	private ListenerList localListeners = new ListenerList(
			ListenerList.IDENTITY);

	public PersonServiceSlave(IServiceLocator locator, IPersonService parent) {
		serviceLocator = locator;
		parentService = parent;
		serviceLocator.hasService(IHandlerService.class);
	}

	public void addPersonChangeListener(IPropertyChangeListener listener) {
		localListeners.add(listener);
		parentService.addPersonChangeListener(listener);
	}

	public Collection getPeople() {
		return parentService.getPeople();
	}

	public Person getPerson(int id) {
		return parentService.getPerson(id);
	}

	public void removePersonChangeListener(IPropertyChangeListener listener) {
		localListeners.remove(listener);
		parentService.removePersonChangeListener(listener);
	}

	public void updatePerson(Person person) {
		parentService.updatePerson(person);
	}

	public Person createPerson(int id) {
		return parentService.createPerson(id);
	}

	public void dispose() {
		Object[] array = localListeners.getListeners();
		localListeners.clear();
		for (int i = 0; i < array.length; i++) {
			parentService
					.removePersonChangeListener((IPropertyChangeListener) array[i]);
		}
		serviceLocator = null;
		parentService = null;
	}

	public void login(Person person) {
		parentService.login(person);
	}

}
