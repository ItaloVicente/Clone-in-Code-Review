
package org.eclipse.jgit.niofs.internal.op.exceptions;

import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;

public class ConcurrentRefUpdateException extends GitException {

    private RefUpdate.Result rc;
    private Ref ref;

    public ConcurrentRefUpdateException(final String message
                                        final Ref ref
                                        final RefUpdate.Result rc) {
        super(rc == null ? message : message + ". " + MessageFormat.format(JGitText.get().refUpdateReturnCodeWas
                                                                           new Object[]{rc}));
        this.rc = rc;
        this.ref = ref;
    }
}
