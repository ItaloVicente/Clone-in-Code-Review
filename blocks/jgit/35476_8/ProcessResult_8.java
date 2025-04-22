package org.eclipse.jgit.util;

public enum Hook {

	private final String name;

	private Hook(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
