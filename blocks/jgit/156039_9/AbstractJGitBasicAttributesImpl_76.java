package org.eclipse.jgit.niofs.internal;

import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.niofs.internal.util.Preconditions;

public abstract class AbstractBasicFileAttributeView<P extends Path>
		implements BasicFileAttributeView

	static final String IS_REGULAR_FILE = "isRegularFile";
	static final String IS_DIRECTORY = "isDirectory";
	static final String IS_SYMBOLIC_LINK = "isSymbolicLink";
	static final String IS_OTHER = "isOther";
	static final String SIZE = "size";
	static final String FILE_KEY = "fileKey";
	static final String LAST_MODIFIED_TIME = "lastModifiedTime";
	static final String LAST_ACCESS_TIME = "lastAccessTime";
	static final String CREATION_TIME = "creationTime";

	private static final Set<String> PROPERTIES = new HashSet<String>() {
		{
			add(IS_REGULAR_FILE);
			add(IS_DIRECTORY);
			add(IS_SYMBOLIC_LINK);
			add(IS_OTHER);
			add(SIZE);
			add(FILE_KEY);
			add(LAST_MODIFIED_TIME);
			add(LAST_ACCESS_TIME);
			add(CREATION_TIME);
		}
	};

	protected final P path;

	public AbstractBasicFileAttributeView(final P path) {
		this.path = checkNotNull("path"
	}

	@Override
	public String name() {
		return "basic";
	}

	@Override
	public void setTimes(final FileTime lastModifiedTime
			throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String
		return readAttributes("*");
	}

	@Override
	public Map<String
		final BasicFileAttributes attrs = readAttributes();

		return new HashMap<String
			{
				for (final String attribute : attributes) {
					Preconditions.checkNotEmpty("attribute"
					if (attribute.equals("*") || attribute.equals(IS_REGULAR_FILE)) {
						put(IS_REGULAR_FILE
					}
					if (attribute.equals("*") || attribute.equals(IS_DIRECTORY)) {
						put(IS_DIRECTORY
					}
					if (attribute.equals("*") || attribute.equals(IS_SYMBOLIC_LINK)) {
						put(IS_SYMBOLIC_LINK
					}
					if (attribute.equals("*") || attribute.equals(IS_OTHER)) {
						put(IS_OTHER
					}
					if (attribute.equals("*") || attribute.equals(SIZE)) {
						put(SIZE
					}
					if (attribute.equals("*") || attribute.equals(FILE_KEY)) {
						put(FILE_KEY
					}
					if (attribute.equals("*") || attribute.equals(LAST_MODIFIED_TIME)) {
						put(LAST_MODIFIED_TIME
					}
					if (attribute.equals("*") || attribute.equals(LAST_ACCESS_TIME)) {
						put(LAST_ACCESS_TIME
					}
					if (attribute.equals("*") || attribute.equals(CREATION_TIME)) {
						put(CREATION_TIME
					}
					if (attribute.equals("*")) {
						break;
					}
				}
			}
		};
	}

	@Override
	public void setAttribute(final String attribute
		Preconditions.checkNotEmpty("attribute"
		Preconditions.checkCondition("invalid attribute"

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSerializable() {
		return false;
	}
}
