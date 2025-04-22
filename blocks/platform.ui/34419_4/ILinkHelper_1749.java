package org.eclipse.ui.navigator;

import org.eclipse.jface.util.IPropertyChangeListener;

public interface IExtensionStateModel {

	String getId();

	String getViewerId();

	String getStringProperty(String aPropertyName);

	boolean getBooleanProperty(String aPropertyName);

	int getIntProperty(String aPropertyName);

	Object getProperty(String aPropertyName);

	void setStringProperty(String aPropertyName, String aPropertyValue);

	void setBooleanProperty(String aPropertyName, boolean aPropertyValue);

	void setIntProperty(String aPropertyName, int aPropertyValue);

	void setProperty(String aPropertyName, Object aPropertyValue);

	void addPropertyChangeListener(IPropertyChangeListener aListener);

	void removePropertyChangeListener(IPropertyChangeListener aListener);
}
