package org.eclipse.jgit.diff;

import org.eclipse.jgit.lib.ObjectId;

public class SubmoduleConflict extends Sequence {
    private final ObjectId objectId;

    public SubmoduleConflict(ObjectId objectId) {
        super();
        this.objectId = objectId;
    }

    @Override
    public int size() {
        return 1;
    }

    public ObjectId getObjectId() {
        return objectId;
    }
}
