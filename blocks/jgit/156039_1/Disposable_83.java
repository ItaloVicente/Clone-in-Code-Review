
package org.eclipse.jgit.niofs.internal;

import java.nio.file.attribute.BasicFileAttributes;

import org.eclipse.jgit.niofs.fs.attribute.BranchDiff;
import org.eclipse.jgit.niofs.fs.attribute.DiffAttributes;

public class DiffAttributesImpl
        extends AbstractJGitBasicAttributesImpl
        implements DiffAttributes {

    private final BranchDiff branchDiff;

    public DiffAttributesImpl(final BasicFileAttributes attributes
                              final BranchDiff branchDiff) {
        super(attributes);
        this.branchDiff = branchDiff;
    }

    @Override
    public BranchDiff branchDiff() {
        return branchDiff;
    }
}
