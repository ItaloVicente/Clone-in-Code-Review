
package org.eclipse.jgit.niofs.fs.attribute;

import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributeView;

public interface DiffAttributeView extends BasicFileAttributeView {

    String name();

    DiffAttributes readAttributes() throws IOException;
}
