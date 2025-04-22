
package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.lib.ObjectId;

public class PackFile extends File {
	private static final long serialVersionUID = 1L;




	private final boolean hasOldPrefix;

	private final PackExt packExt;

	private static String createName(String id
		return PREFIX + id + '.' + extension.getExtension();
	}

	public PackFile(File file) {
		this(file.getParentFile()
	}

	public PackFile(File directory
		this(directory
	}

	public PackFile(File directory
		this(directory
	}

	public PackFile(File directory
		super(directory
		int dot = name.lastIndexOf('.');

		if (dot < 0) {
			base = name;
			hasOldPrefix = false;
			packExt = null;
		} else {
			base = name.substring(0
			packExt = getPackExt(tail);
			String old = tail.substring(0
					tail.length() - getExtension().length());
			hasOldPrefix = old.equals(getExtPrefix(true));
		}

		id = base.startsWith(PREFIX) ? base.substring(PREFIX.length()) : base;
	}

	public String getId() {
		return id;
	}

	public PackExt getPackExt() {
		return packExt;
	}

	public PackFile create(PackExt ext) {
		return new PackFile(getParentFile()
	}

	public PackFile createForDirectory(File directory) {
		return new PackFile(directory
	}

	public PackFile createPreservedForDirectory(File directory) {
		return new PackFile(directory
	}

	private String getName(PackExt ext) {
		return base + '.' + getExtPrefix(hasOldPrefix) + ext.getExtension();
	}

	private String getName(boolean isPreserved) {
		return base + '.' + getExtPrefix(isPreserved) + getExtension();
	}

	private String getExtension() {
	}

	private static String getExtPrefix(boolean isPreserved) {
	}

	private static PackExt getPackExt(String endsWithExtension) {
		for (PackExt ext : PackExt.values()) {
			if (endsWithExtension.endsWith(ext.getExtension())) {
				return ext;
			}
		}
		throw new IllegalArgumentException(MessageFormat.format(
				JGitText.get().unrecognizedPackExtension
	}
}
