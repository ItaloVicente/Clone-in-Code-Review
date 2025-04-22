
package org.eclipse.jgit.niofs.internal;

import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.niofs.fs.attribute.HiddenAttributeView;
import org.eclipse.jgit.niofs.fs.attribute.HiddenAttributes;

public abstract class HiddenAttributeViewImpl<P extends Path>
        extends AbstractBasicFileAttributeView<P> implements HiddenAttributeView {

    public static final String HIDDEN = "hidden";

    public HiddenAttributeViewImpl(final P path) {
        super(path);
    }

    @Override
    public String name() {
        return HIDDEN;
    }

    @Override
    public Map<String
        final HiddenAttributes attrs = readAttributes();

        return new HashMap<String

            for (final String attribute : attributes) {
                checkNotEmpty("attribute"
                              attribute);

                if (attribute.equals("*") || attribute.equals(HIDDEN)) {
                    put(HIDDEN
                        attrs.isHidden());
                }

                if (attribute.equals("*")) {
                    break;
                }
            }
        }};
    }
}
