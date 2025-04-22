
package org.eclipse.jgit.niofs.fs.attribute;

import java.nio.file.attribute.BasicFileAttributes;

public interface DiffAttributes extends BasicFileAttributes {

    BranchDiff branchDiff();
}
