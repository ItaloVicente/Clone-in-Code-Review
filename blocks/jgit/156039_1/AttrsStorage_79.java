
package org.eclipse.jgit.niofs.internal;

import java.nio.file.attribute.AttributeView;

public interface AttrHolder {

    AttrsStorage getAttrStorage();

    <V extends AttributeView> void addAttrView(final V view);

    <V extends AttributeView> V getAttrView(final Class<V> type);

    <V extends AttributeView> V getAttrView(final String name);
}
