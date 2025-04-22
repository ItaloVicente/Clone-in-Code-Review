package org.eclipse.ui.examples.navigator;

import org.eclipse.core.resources.IFile;

public class PropertiesTreeData { 

	private IFile container; 
	private String name;  
	private String value;

	public PropertiesTreeData(String aName, String aValue, IFile aFile) { 
		name = aName;
		value = aValue;
		container = aFile; 
	} 
 
	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public IFile getFile() { 
		return container;
	}

	public int hashCode() {
		return name.hashCode();
	}

	public boolean equals(Object obj) {
		return obj instanceof PropertiesTreeData
				&& ((PropertiesTreeData) obj).getName().equals(name);
	} 

	public String toString() {
		StringBuffer toString = 
				new StringBuffer(getName()).append(":").append(getValue()); //$NON-NLS-1$
		return toString.toString();
	}


}
