
package org.eclipse.jgit.attributes;


public class Attribute {

	public static enum State {
		SET

		UNSET

		CUSTOM
	}

	private final String key;
	private final State state;
	private final String value;

	public Attribute(String key
		this(key
	}


	private Attribute(String key
		this.key = key;
		this.state = state != null ? state : State.CUSTOM;
		this.value = value;
	}

	public Attribute(String key
		this(key
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attribute other = (Attribute) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (state != other.state)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public String getKey() {
		return key;
	}

	public State getState() {
		return state;
	}


	public String getValue() {
		return value;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}


	@Override
	public String toString() {
		switch (state) {
		case SET:
			return key;
		case UNSET:
		case CUSTOM:
		default:
		}
	}
}
