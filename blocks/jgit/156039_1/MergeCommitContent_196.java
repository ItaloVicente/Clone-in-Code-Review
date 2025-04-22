
package org.eclipse.jgit.niofs.internal.op.model;

import java.io.File;
import java.util.Map;

public class DefaultCommitContent implements CommitContent {

    private final Map<String

    public DefaultCommitContent(final Map<String
        this.content = content;
    }

    public Map<String
        return content;
    }
}
