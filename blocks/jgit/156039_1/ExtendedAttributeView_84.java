
package org.eclipse.jgit.niofs.internal;

import java.io.IOException;

public interface Disposable {

    void dispose() throws IOException;
}
