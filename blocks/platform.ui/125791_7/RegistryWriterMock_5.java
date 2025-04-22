package org.eclipse.urischeme.internal.registration;

public class NameAndTypeMock {

	String name;
	String type;

	NameAndTypeMock(String attribute, String value) {
		this.name = attribute;
		this.type = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof NameAndTypeMock)) {
			return false;
		}
		NameAndTypeMock nameAndTypeMock = (NameAndTypeMock) obj;
		if ((this.name == null) ? (nameAndTypeMock.name != null) : !this.name.equals(nameAndTypeMock.name)) {
			return false;
		}
		if ((this.type == null) ? (nameAndTypeMock.type != null) : !this.type.equals(nameAndTypeMock.type)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
        int hash = 17;
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 53 * hash + (this.type != null ? this.type.hashCode() : 0);
        return hash;
	}
}
