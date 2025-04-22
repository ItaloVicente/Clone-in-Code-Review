
package org.eclipse.jgit.niofs.internal;

import java.nio.file.attribute.AttributeView;
import java.nio.file.attribute.BasicFileAttributeView;
import java.util.HashMap;
import java.util.Map;

public class AttrsStorageImpl implements AttrsStorage {

    final Properties content = new Properties();
    final Map<String
    final Map<Class<?>

    @Override
    public AttrsStorage getAttrStorage() {
        return this;
    }

    @Override
    public <V extends AttributeView> void addAttrView(final V view) {
        viewsNameIndex.put(view.name()
                           view);
        if (view instanceof ExtendedAttributeView) {
            final ExtendedAttributeView extendedView = (ExtendedAttributeView) view;
            for (Class<? extends BasicFileAttributeView> type : extendedView.viewTypes()) {
                viewsTypeIndex.put(type
                                   view);
            }
        } else {
            viewsTypeIndex.put(view.getClass()
                               view);
        }
    }

    @Override
    public <V extends AttributeView> V getAttrView(final Class<V> type) {
        return (V) viewsTypeIndex.get(type);
    }

    @Override
    public <V extends AttributeView> V getAttrView(final String name) {
        return (V) viewsNameIndex.get(name);
    }

    @Override
    public void clear() {
        viewsNameIndex.clear();
        viewsTypeIndex.clear();
        content.clear();
    }
}

