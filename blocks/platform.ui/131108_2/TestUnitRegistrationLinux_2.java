package org.eclipse.urischeme.internal.registration;

import org.eclipse.urischeme.IScheme;

public class Scheme implements IScheme {

	private String name;
	private String desription;

	public Scheme(String name, String desription) {
		this.name = name;
		this.desription = desription;

	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return desription;
	}

}
