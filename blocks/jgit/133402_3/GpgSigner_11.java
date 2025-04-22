package org.eclipse.jgit.lib;

import java.io.Serializable;

public class GpgSignature implements Serializable {

	private static final long serialVersionUID = 1L;

	public String toExternalString() {
		return null;
	}

	@Override
	@SuppressWarnings("nls")
	public String toString() {
		final StringBuilder r = new StringBuilder();

		r.append("GpgSignature[");
		r.append("]");

		return r.toString();
	}
}
