package org.eclipse.jgit.transport;

public abstract class Credentials {

	public abstract String toString();

	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object obj);
}
