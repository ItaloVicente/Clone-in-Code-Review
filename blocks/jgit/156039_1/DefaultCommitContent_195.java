
package org.eclipse.jgit.niofs.internal.op.model;

import java.util.Map;

public class CopyCommitContent implements CommitContent {

    private final Map<String

    public CopyCommitContent(final Map<String
        this.content = content;
    }

    public Map<String
        return content;
    }
}
