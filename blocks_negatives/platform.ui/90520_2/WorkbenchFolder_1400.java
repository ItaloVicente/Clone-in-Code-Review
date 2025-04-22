/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.ide.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.WorkbenchPlugin;

/**
 * An IWorkbenchAdapter that represents IFiles.
 */
public class WorkbenchFile extends WorkbenchResource {

	/**
	 * Constant that is used as the key of a session property on IFile objects
	 * to cache the result of doing a proper content type lookup. This will be
	 * set by the ContentTypeDecorator (if enabled) and used instead of the
	 * "guessed" content type in {@link #getBaseImage(IResource)}.
	 *
	 * @since 3.4
	 */
	public static QualifiedName IMAGE_CACHE_KEY = new QualifiedName(WorkbenchPlugin.PI_WORKBENCH, "WorkbenchFileImage"); //$NON-NLS-1$

	/**
     *	Answer the appropriate base image to use for the passed resource, optionally
     *	considering the passed open status as well iff appropriate for the type of
     *	passed resource
     */
    @Override
	protected ImageDescriptor getBaseImage(IResource resource) {
		IContentType contentType = null;
		if (resource instanceof IFile) {
			IFile file = (IFile)resource;
			ImageDescriptor cached;
			try {
				cached = (ImageDescriptor) file.getSessionProperty(IMAGE_CACHE_KEY);
				if (cached != null) {
					return cached;
				}
			} catch (CoreException e) {
			}
			contentType = IDE.guessContentType(file);
		}
        ImageDescriptor image = PlatformUI.getWorkbench().getEditorRegistry()
                .getImageDescriptor(resource.getName(), contentType);
        if (image == null) {
			image = PlatformUI.getWorkbench().getSharedImages()
                    .getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
		}
        return image;
    }
}
