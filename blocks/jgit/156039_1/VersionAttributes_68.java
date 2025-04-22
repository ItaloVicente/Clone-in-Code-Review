
package org.eclipse.jgit.niofs.fs.attribute;

import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributeView;

public interface VersionAttributeView extends BasicFileAttributeView {

    String name();

    VersionAttributes readAttributes() throws IOException;
}
