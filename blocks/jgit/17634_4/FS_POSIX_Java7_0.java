package org.eclipse.jgit.util;

public class PathMatcherJava7Test extends PathMatcherTest {
	@Override
	protected PathMatcher getPathMatcher(String glob) {
		return new PathMatcher_Java7(glob);
	}
}
