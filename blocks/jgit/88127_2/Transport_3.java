
package org.eclipse.jgit.transport;

import java.io.Serializable;

import org.eclipse.jgit.internal.JGitText;

public class RefLeaseSpec implements Serializable {
	private static final long serialVersionUID = 1L;

	private String ref;

	private String expected;

	private RefLeaseSpec(final RefLeaseSpec p) {
		ref = p.getRef();
		expected = p.getExpected();
	}

	public RefLeaseSpec(final String ref
		this.ref = ref;
		this.expected = expected;
	}

	public String getRef() {
		return ref;
	}

	public String getExpected() {
		return expected;
	}

	public String toString() {
		final StringBuilder r = new StringBuilder();
		r.append(getRef());
		r.append(':');
		r.append(getExpected());
		return r.toString();
	}
}
