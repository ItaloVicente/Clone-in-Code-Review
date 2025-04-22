package org.eclipse.jgit.niofs.fs.attribute;

import java.nio.file.attribute.BasicFileAttributes;

public interface HiddenAttributes extends BasicFileAttributes {

	boolean isHidden();
}
