
package org.eclipse.jgit.transport;

import java.io.Serializable;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;

public class RefSpec implements Serializable {
	private static final long serialVersionUID = 1L;


	public static boolean isWildcard(String s) {
	}

	private boolean force;

	private boolean wildcard;

	public enum WildcardMode {
		REQUIRE_MATCH
		ALLOW_MISMATCH
	}
	private WildcardMode allowMismatchedWildcards;

	private String srcName;

	private String dstName;

	public RefSpec() {
		force = false;
		wildcard = false;
		srcName = Constants.HEAD;
		dstName = null;
		allowMismatchedWildcards = WildcardMode.REQUIRE_MATCH;
	}

	public RefSpec(String spec
		this.allowMismatchedWildcards = mode;
		String s = spec;
			force = true;
			s = s.substring(1);
		}

		final int c = s.lastIndexOf(':');
		if (c == 0) {
			s = s.substring(1);
			if (isWildcard(s)) {
				wildcard = true;
				if (mode == WildcardMode.REQUIRE_MATCH) {
					throw new IllegalArgumentException(MessageFormat
							.format(JGitText.get().invalidWildcards
				}
			}
			dstName = checkValid(s);
		} else if (c > 0) {
			String src = s.substring(0
			String dst = s.substring(c + 1);
			if (isWildcard(src) && isWildcard(dst)) {
				wildcard = true;
			} else if (isWildcard(src) || isWildcard(dst)) {
				wildcard = true;
				if (mode == WildcardMode.REQUIRE_MATCH)
					throw new IllegalArgumentException(MessageFormat
							.format(JGitText.get().invalidWildcards
			}
			srcName = checkValid(src);
			dstName = checkValid(dst);
		} else {
			if (isWildcard(s)) {
				if (mode == WildcardMode.REQUIRE_MATCH) {
					throw new IllegalArgumentException(MessageFormat
							.format(JGitText.get().invalidWildcards
				}
				wildcard = true;
			}
			srcName = checkValid(s);
		}
	}

	public RefSpec(String spec) {
		this(spec
	}

	private RefSpec(RefSpec p) {
		force = p.isForceUpdate();
		wildcard = p.isWildcard();
		srcName = p.getSource();
		dstName = p.getDestination();
		allowMismatchedWildcards = p.allowMismatchedWildcards;
	}

	public boolean isForceUpdate() {
		return force;
	}

	public RefSpec setForceUpdate(boolean forceUpdate) {
		final RefSpec r = new RefSpec(this);
		r.force = forceUpdate;
		return r;
	}

	public boolean isWildcard() {
		return wildcard;
	}

	public String getSource() {
		return srcName;
	}

	public RefSpec setSource(String source) {
		final RefSpec r = new RefSpec(this);
		r.srcName = checkValid(source);
		if (isWildcard(r.srcName) && r.dstName == null)
			throw new IllegalStateException(JGitText.get().destinationIsNotAWildcard);
		if (isWildcard(r.srcName) != isWildcard(r.dstName))
			throw new IllegalStateException(JGitText.get().sourceDestinationMustMatch);
		return r;
	}

	public String getDestination() {
		return dstName;
	}

	public RefSpec setDestination(String destination) {
		final RefSpec r = new RefSpec(this);
		r.dstName = checkValid(destination);
		if (isWildcard(r.dstName) && r.srcName == null)
			throw new IllegalStateException(JGitText.get().sourceIsNotAWildcard);
		if (isWildcard(r.srcName) != isWildcard(r.dstName))
			throw new IllegalStateException(JGitText.get().sourceDestinationMustMatch);
		return r;
	}

	public RefSpec setSourceDestination(final String source
			final String destination) {
		if (isWildcard(source) != isWildcard(destination))
			throw new IllegalStateException(JGitText.get().sourceDestinationMustMatch);
		final RefSpec r = new RefSpec(this);
		r.wildcard = isWildcard(source);
		r.srcName = source;
		r.dstName = destination;
		return r;
	}

	public boolean matchSource(String r) {
		return match(r
	}

	public boolean matchSource(Ref r) {
		return match(r.getName()
	}

	public boolean matchDestination(String r) {
		return match(r
	}

	public boolean matchDestination(Ref r) {
		return match(r.getName()
	}

	public RefSpec expandFromSource(String r) {
		if (allowMismatchedWildcards != WildcardMode.REQUIRE_MATCH) {
			throw new IllegalStateException(
					JGitText.get().invalidExpandWildcard);
		}
		return isWildcard() ? new RefSpec(this).expandFromSourceImp(r) : this;
	}

	private RefSpec expandFromSourceImp(String name) {
		final String psrc = srcName
		wildcard = false;
		srcName = name;
		dstName = expandWildcard(name
		return this;
	}

	public RefSpec expandFromSource(Ref r) {
		return expandFromSource(r.getName());
	}

	public RefSpec expandFromDestination(String r) {
		if (allowMismatchedWildcards != WildcardMode.REQUIRE_MATCH) {
			throw new IllegalStateException(
					JGitText.get().invalidExpandWildcard);
		}
		return isWildcard() ? new RefSpec(this).expandFromDstImp(r) : this;
	}

	private RefSpec expandFromDstImp(String name) {
		final String psrc = srcName
		wildcard = false;
		srcName = expandWildcard(name
		dstName = name;
		return this;
	}

	public RefSpec expandFromDestination(Ref r) {
		return expandFromDestination(r.getName());
	}

	private boolean match(String name
		if (s == null)
			return false;
		if (isWildcard(s)) {
			int wildcardIndex = s.indexOf('*');
			String prefix = s.substring(0
			String suffix = s.substring(wildcardIndex + 1);
			return name.length() > prefix.length() + suffix.length()
					&& name.startsWith(prefix) && name.endsWith(suffix);
		}
		return name.equals(s);
	}

	private static String expandWildcard(String name
			String patternB) {
		int a = patternA.indexOf('*');
		int trailingA = patternA.length() - (a + 1);
		int b = patternB.indexOf('*');
		String match = name.substring(a
		return patternB.substring(0
	}

	private static String checkValid(String spec) {
		if (spec != null && !isValid(spec))
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().invalidRefSpec
		return spec;
	}

	private static boolean isValid(String s) {
			return false;
			return false;
			return false;
		int i = s.indexOf('*');
		if (i != -1) {
			if (s.indexOf('*'
				return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hc = 0;
		if (getSource() != null)
			hc = hc * 31 + getSource().hashCode();
		if (getDestination() != null)
			hc = hc * 31 + getDestination().hashCode();
		return hc;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RefSpec))
			return false;
		final RefSpec b = (RefSpec) obj;
		if (isForceUpdate() != b.isForceUpdate())
			return false;
		if (isWildcard() != b.isWildcard())
			return false;
		if (!eq(getSource()
			return false;
		if (!eq(getDestination()
			return false;
		return true;
	}

	private static boolean eq(String a
		if (a == b)
			return true;
		if (a == null || b == null)
			return false;
		return a.equals(b);
	}

	@Override
	public String toString() {
		final StringBuilder r = new StringBuilder();
		if (isForceUpdate())
			r.append('+');
		if (getSource() != null)
			r.append(getSource());
		if (getDestination() != null) {
			r.append(':');
			r.append(getDestination());
		}
		return r.toString();
	}
}
