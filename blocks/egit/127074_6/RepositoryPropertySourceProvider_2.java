package org.eclipse.egit.ui.internal.repository;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.ui.internal.PreferenceBasedDateFormatter;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class PersonIdentPropertySource implements IPropertySource {

	private static final String PROPERTY_NAME = "name"; //$NON-NLS-1$

	private static final String PROPERTY_EMAIL = "e-mail"; //$NON-NLS-1$

	private static final String PROPERTY_DATE = "date"; //$NON-NLS-1$

	private final PersonIdent person;

	private final IPropertyDescriptor[] descriptors;

	public PersonIdentPropertySource(PersonIdent ident) {
		person = ident;
		List<PropertyDescriptor> result = new ArrayList<>(3);
		result.add(new PropertyDescriptor(PROPERTY_NAME,
				UIText.PersonIdentPropertySource_Name));
		result.add(new PropertyDescriptor(PROPERTY_EMAIL,
				UIText.PersonIdentPropertySource_Email));
		result.add(new PropertyDescriptor(PROPERTY_DATE,
				UIText.PersonIdentPropertySource_Date));
		String category = ident.toExternalString();
		for (PropertyDescriptor desc : result) {
			desc.setCategory(category);
		}
		descriptors = result.toArray(new IPropertyDescriptor[0]);
	}

	@Override
	public Object getEditableValue() {
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return descriptors;
	}

	@Override
	public Object getPropertyValue(Object id) {
		switch (id.toString()) {
		case PROPERTY_NAME:
			return person.getName();
		case PROPERTY_EMAIL:
			return person.getEmailAddress();
		case PROPERTY_DATE:
			return PreferenceBasedDateFormatter.create().formatDate(person);
		default:
			return null;
		}
	}

	@Override
	public boolean isPropertySet(Object id) {
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
	}

	@Override
	public String toString() {
		return person.toExternalString();
	}
}
