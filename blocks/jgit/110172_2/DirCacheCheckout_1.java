package org.eclipse.jgit.diff;

import org.eclipse.jgit.lib.ObjectId;

public class SubmoduleSequence extends Sequence{
    private final ObjectId objectId;

    public SubmoduleSequence(ObjectId objectId) {
        super();
        this.objectId = objectId;
    }

    @Override
    public int size() {
        return 1;
    }
}
