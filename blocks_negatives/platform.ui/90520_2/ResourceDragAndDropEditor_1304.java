/*******************************************************************************
 * Copyright (c) 2000, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Lars Vogel <Lars.Vogel@gmail.com> - Bug 430694
 *******************************************************************************/
package org.eclipse.ui.internal.ide;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;

/**
 * Implementation of IMarkerImageProvider to provide the image
 * path names for problem markers.
 */
public class ProblemImageProvider implements IMarkerImageProvider {
    /**
     * TaskImageProvider constructor comment.
     */
    public ProblemImageProvider() {
        super();
    }

    /**
     * Returns the relative path for the image
     * to be used for displaying an marker in the workbench.
     * This path is relative to the plugin location
     *
     * Returns <code>null</code> if there is no appropriate image.
     *
     * @param marker The marker to get an image path for.
     *
     */
    @Override
	public String getImagePath(IMarker marker) {
        if (isMarkerType(marker, IMarker.PROBLEM)) {
            switch (marker.getAttribute(IMarker.SEVERITY,
                    IMarker.SEVERITY_WARNING)) {
            case IMarker.SEVERITY_ERROR:
            case IMarker.SEVERITY_WARNING:
            case IMarker.SEVERITY_INFO:
            }
        }
        return null;
    }

    /**
     * Returns whether the given marker is of the given type (either directly or indirectly).
     */
    private boolean isMarkerType(IMarker marker, String type) {
        try {
            return marker.isSubtypeOf(type);
        } catch (CoreException e) {
            return false;
        }
    }
}
