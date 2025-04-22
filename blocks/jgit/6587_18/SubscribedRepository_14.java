
package org.eclipse.jgit.transport;

public class SubscribeSpec {
	public static String stripWildcard(String ref) {
		return ref.substring(0
	}

	private final String refName;

	private final boolean isWildcard;

	public SubscribeSpec(String name) {
		refName = name;
		isWildcard = RefSpec.isWildcard(name);
	}

	public String getRefName() {
		return refName;
	}

	public boolean isWildcard() {
		return isWildcard;
	}

	public boolean isMatch(String ref) {
		if (ref == null)
			return false;
		if (isWildcard)
			return ref.startsWith(stripWildcard(refName));
		return refName.equals(ref);
	}

	@Override
	public int hashCode() {
		return refName.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof SubscribeSpec))
			return false;
		return ((SubscribeSpec) other).getRefName().equals(this.getRefName());
	}

	@Override
	public String toString() {
		return "SubscribeSpec[" + refName + "]";
	}
}
