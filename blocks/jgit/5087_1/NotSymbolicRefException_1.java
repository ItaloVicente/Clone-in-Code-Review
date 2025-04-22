package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NotSymbolicRefException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.RefUpdateException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;

public class SymbolicRefCommand extends GitCommand<String> {

    private String name;
    private String message;
    private String newRef;

    protected SymbolicRefCommand(Repository repo) {
        super(repo);
    }

    public SymbolicRefCommand setName(String name) {
        checkCallable();
        this.name = name;
        return this;
    }

    public SymbolicRefCommand setMessage(String message) {
        checkCallable();
        this.message = message;
        return this;
    }

    public SymbolicRefCommand setNewRef(String ref) {
        checkCallable();
        this.newRef = ref;
        return this;
    }

    public String call() throws Exception {
        try {
            Ref ref = repo.getRef(name);
            if (ref == null) {
                if (newRef == null) {
                    throw new RefNotFoundException(JGitText.get().noSuchRef);
                    tryUpdateRef();
                    return null;
                }
            } else {
                if (ref.isSymbolic()) {
                    if (newRef == null) {
                        return ref.getTarget().getName();
                        tryUpdateRef();
                        return null;
                    }
                } else {
                    throw new NotSymbolicRefException(MessageFormat.format(JGitText.get().aSymbolicRefRequired
                }
            }
        } catch (IOException ioe) {
            throw new JGitInternalException(ioe.getMessage()
        }
    }

    private RefUpdate.Result tryUpdateRef() throws IOException
        RefUpdate refUpdate = repo.updateRef(name);
        if (message != null) {
            refUpdate.setRefLogMessage(message
        }
        RefUpdate.Result updateResult = refUpdate.link(newRef);

        setCallable(false);

        if (updateResult == RefUpdate.Result.NEW || updateResult == RefUpdate.Result.FORCED) {
            return updateResult;
        } else {
            throw new RefUpdateException(null
        }
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getNewRef() {
        return newRef;
    }
}
