
package org.eclipse.ui.examples.contributions.model;

import java.util.Collection;

import org.eclipse.jface.util.IPropertyChangeListener;

public interface IPersonService {
	public static final String PROP_ADD = "add"; //$NON-NLS-1$

	public static final String PROP_CHANGE = "change"; //$NON-NLS-1$

	public Collection getPeople();

	public Person getPerson(int id);

	public void updatePerson(Person person);

	public Person createPerson(int id);

	public void addPersonChangeListener(IPropertyChangeListener listener);

	public void removePersonChangeListener(IPropertyChangeListener listener);

	public void login(Person person);
}
