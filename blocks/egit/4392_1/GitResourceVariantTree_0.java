package org.eclipse.egit.core;

import org.eclipse.core.resources.IResource;

public class Helpers {

	private Helpers() {
	}

	public static boolean isNonWorksapce(IResource resource) {
		return resource.getLocation() == null;
	}

}
