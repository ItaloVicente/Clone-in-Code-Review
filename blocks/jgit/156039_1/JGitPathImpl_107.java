
package org.eclipse.jgit.niofs.internal;

import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributeView;

import org.eclipse.jgit.niofs.fs.attribute.HiddenAttributeView;
import org.eclipse.jgit.niofs.fs.attribute.HiddenAttributes;
import org.eclipse.jgit.niofs.internal.daemon.filter.HiddenBranchRefFilter;

public class JGitHiddenAttributeViewImpl extends HiddenAttributeViewImpl<JGitPathImpl> {

    private HiddenAttributes attrs = null;

    public JGitHiddenAttributeViewImpl(final JGitPathImpl path) {
        super(path);
    }

    @Override
    public HiddenAttributes readAttributes() throws IOException {
        if (attrs == null) {
            attrs = buildAttrs(path.getFileSystem()
                               path.getRefTree()
                               path.getPath());
        }
        return attrs;
    }

    @Override
    public Class<? extends BasicFileAttributeView>[] viewTypes() {
        return new Class[]{HiddenAttributeView.class
    }

    private HiddenAttributes buildAttrs(final JGitFileSystem fileSystem
                                        final String refTree
                                        final String path) throws IOException {
        return new HiddenAttributesImpl(new JGitBasicAttributeView(this.path).readAttributes()
                                        HiddenBranchRefFilter.isHidden(refTree));
    }
}
