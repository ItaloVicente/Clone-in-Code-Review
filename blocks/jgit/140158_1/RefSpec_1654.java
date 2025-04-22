
package org.eclipse.jgit.transport;

import java.io.Serializable;

public class RefLeaseSpec implements Serializable {
	private static final long serialVersionUID = 1L;

	private final String ref;

	private final String expected;

	public RefLeaseSpec(String ref
		this.ref = ref;
		this.expected = expected;
	}

	public String getRef() {
		return ref;
	}

	public String getExpected() {
		return expected;
	}

	@Override
	public String toString() {
		final StringBuilder r = new StringBuilder();
		r.append(getRef());
		r.append(':');
		r.append(getExpected());
		return r.toString();
	}
}
