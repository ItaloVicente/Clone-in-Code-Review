package org.eclipse.jgit.util;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class PathMatcher_Java7 implements PathMatcher {
	private final java.nio.file.PathMatcher matcher;

	public PathMatcher_Java7(String globPattern) {
		matcher = FileSystems.getDefault()
	}

	@Override
	public boolean matches(String pathString) {
		final Path path = FileSystems.getDefault().getPath(pathString);
		return matcher.matches(path);
	}
}
