/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Joe Bowbeer (jozart@blarg.net) - removed dependency on runtime compatibility layer (bug 74526)
 *******************************************************************************/
package org.eclipse.ui.examples.readmetool;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * Convenience class for storing references to image descriptors
 * used by the readme tool.
 */
public class ReadmeImages {

    static final ImageDescriptor EDITOR_ACTION1_IMAGE;

    static final ImageDescriptor EDITOR_ACTION2_IMAGE;

    static final ImageDescriptor EDITOR_ACTION3_IMAGE;

    static final ImageDescriptor EDITOR_ACTION1_IMAGE_DISABLE;

    static final ImageDescriptor EDITOR_ACTION2_IMAGE_DISABLE;

    static final ImageDescriptor EDITOR_ACTION3_IMAGE_DISABLE;

    static final ImageDescriptor EDITOR_ACTION1_IMAGE_ENABLE;

    static final ImageDescriptor EDITOR_ACTION2_IMAGE_ENABLE;

    static final ImageDescriptor EDITOR_ACTION3_IMAGE_ENABLE;

    static final ImageDescriptor README_WIZARD_BANNER;

    static {


        EDITOR_ACTION1_IMAGE_DISABLE = createImageDescriptor(prefix
        EDITOR_ACTION2_IMAGE_DISABLE = createImageDescriptor(prefix
        EDITOR_ACTION3_IMAGE_DISABLE = createImageDescriptor(prefix

        EDITOR_ACTION1_IMAGE_ENABLE = createImageDescriptor(prefix
        EDITOR_ACTION2_IMAGE_ENABLE = createImageDescriptor(prefix
        EDITOR_ACTION3_IMAGE_ENABLE = createImageDescriptor(prefix

        README_WIZARD_BANNER = createImageDescriptor(prefix
    }

    /**
     * Utility method to create an <code>ImageDescriptor</code>
     * from a path to a file.
     */
    private static ImageDescriptor createImageDescriptor(String path) {
        try {
            URL url = new URL(BASE_URL, path);
            return ImageDescriptor.createFromURL(url);
        } catch (MalformedURLException e) {
        }
        return ImageDescriptor.getMissingImageDescriptor();
    }
}
