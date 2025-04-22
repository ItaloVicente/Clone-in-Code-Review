/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.examples.readmetool;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.part.IDropActionDelegate;

/**
 * Adapter for handling the dropping of readme segments into
 * another plugin.  In this case, we expect the segments
 * to be dropped onto <code>IFile</code> object, or an adapter
 * that supports <code>IFile</code>.
 */
public class ReadmeDropActionDelegate implements IDropActionDelegate {

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
                return false;
            }
            return true;
        }
        return false;
    }
}
