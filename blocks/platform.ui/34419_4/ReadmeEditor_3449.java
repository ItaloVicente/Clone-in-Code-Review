package org.eclipse.ui.examples.readmetool;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.part.IDropActionDelegate;

public class ReadmeDropActionDelegate implements IDropActionDelegate {
    public static final String ID = "org_eclipse_ui_examples_readmetool_drop_actions"; //$NON-NLS-1$

    @Override
	public boolean run(Object source, Object target) {
        if (source instanceof byte[] && target instanceof IFile) {
            IFile file = (IFile) target;
            try {
                file.appendContents(new ByteArrayInputStream((byte[]) source),
                        false, true, null);
            } catch (CoreException e) {
                System.out
                        .println(MessageUtil
                                .getString("Exception_in_readme_drop_adapter") + e.getStatus().getMessage()); //$NON-NLS-1$
                return false;
            }
            return true;
        }
        return false;
    }
}
