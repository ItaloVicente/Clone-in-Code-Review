
package org.eclipse.jgit.niofs.internal.op.commands;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

import static org.eclipse.jgit.lib.Constants.OBJ_TREE;

public class GetRef {

    private final Repository repo;
    private final String name;

    public GetRef(final Repository repo
                  final String name) {
        this.repo = repo;
        this.name = name;
    }

    public Ref execute() {
        try {
            final Ref value = repo.getRefDatabase().findRef(name);
            if (value != null) {
                return value;
            }
            final ObjectId treeRef = repo.resolve(name + "^{tree}");
            if (treeRef != null) {
                try (final ObjectReader objectReader = repo.getObjectDatabase().newReader()) {
                    final ObjectLoader loader = objectReader.open(treeRef);
                    if (loader.getType() == OBJ_TREE) {
                        return new ObjectIdRef.PeeledTag(Ref.Storage.NEW
                                                         name
                                                         ObjectId.fromString(name)
                                                         treeRef);
                    }
                }
            }
        } catch (final Exception ignored) {
        }
        return null;
    }
}
