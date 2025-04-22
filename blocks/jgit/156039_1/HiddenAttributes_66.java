
package org.eclipse.jgit.niofs.fs.attribute;

import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributeView;

public interface HiddenAttributeView extends BasicFileAttributeView {

    String name();

    HiddenAttributes readAttributes() throws IOException;
}
