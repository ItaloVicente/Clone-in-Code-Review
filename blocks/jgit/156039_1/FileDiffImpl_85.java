
package org.eclipse.jgit.niofs.internal;

import java.io.IOException;
import java.nio.file.attribute.AttributeView;
import java.util.Map;

public interface ExtendedAttributeView extends AttributeView {

    Map<String

    Map<String

    void setAttribute(final String attribute
                      final Object value) throws IOException;

    Class[] viewTypes();

    boolean isSerializable();
}
