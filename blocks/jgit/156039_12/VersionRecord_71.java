package org.eclipse.jgit.niofs.fs.attribute;

import java.util.List;

public interface VersionHistory {

	List<VersionRecord> records();
}
