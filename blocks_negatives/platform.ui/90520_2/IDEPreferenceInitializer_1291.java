/*******************************************************************************
 * Copyright (c) 2003, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.ide;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.PlatformUI;

/**
 * This class defines internal constants for images that intended to be
 * available only within the IDE UI proper. Use
 * <code>IDEInternalWorkbenchImages.getImageDescriptor} to retrieve image
 * descriptors for any of the images named in this class.
 * <p>
 * <b>Note:</b> The existence of these images is not made publically available
 * because such images are subject to change as the dialogs evolve through
 * successive releases.
 * </p>
 */
public final class IDEInternalWorkbenchImages {

    /** Block instantiation. */
    private IDEInternalWorkbenchImages() {
    }

    /*** Constants for Images ***/




































    /**
     * Returns the image descriptor for the workbench image with the given
     * symbolic name. Use this method to retrieve image descriptors for any
     * of the images named in this class.
     *
     * @param symbolicName the symbolic name of the image
     * @return the image descriptor, or <code>null</code> if none
     */
    public static ImageDescriptor getImageDescriptor(String symbolicName) {
        return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(
                symbolicName);
    }
}
