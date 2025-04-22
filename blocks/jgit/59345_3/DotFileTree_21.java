
package org.eclipse.jgit.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.Constants;

public class DotFileCollector implements FileVisitor<Path> {
	private final ArrayList<File> collector = new ArrayList<>();

	public List<File> getCollectedFiles() {
		return collector;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir
			BasicFileAttributes attrs) throws IOException {
		if (dir.endsWith(Constants.DOT_GIT))
			return FileVisitResult.SKIP_SUBTREE;
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file
			throws IOException {
		if (attrs.isRegularFile()) {
			String name = file.getFileName().toString();
			if (name.charAt(0) == '.') {
				switch (name) {
				case Constants.DOT_GIT_ATTRIBUTES:
				case Constants.DOT_GIT_IGNORE:
					collector.add(file.toFile());
					break;
				}
			}
		}
		return FileVisitResult.CONTINUE;
	}

	public void visitFile(File file) {
		String name = file.getName();
		if (name.charAt(0) == '.') {
			switch (name) {
			case Constants.DOT_GIT_ATTRIBUTES:
			case Constants.DOT_GIT_IGNORE:
				collector.add(file);
				break;
			}
		}
	}

	@Override
	public FileVisitResult visitFileFailed(Path file
			throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir
			throws IOException {
		return FileVisitResult.CONTINUE;
	}
}
