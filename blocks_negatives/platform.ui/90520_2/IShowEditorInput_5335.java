/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Device;

/**
 * A registry for common images used by the workbench which may be useful
 * to other plug-ins.
 * <p>
 * This class provides <code>Image</code> and <code>ImageDescriptor</code>s
 * for each named image in the interface.  All <code>Image</code> objects provided
 * by this class are managed by this class and must never be disposed
 * by other clients.
 * </p>
 * <p>
 * This interface is not intended to be implemented by clients.
 * </p>
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ISharedImages {
    /**
     * Identifies the error overlay image.
     * @since 3.4
     */

    /**
     * Identifies the warning overlay image.
     * @since 3.4
     */

    /**
     * Identifies the default image used for views.
     */

    /**
     * Identifies the collapse all image in the enabled state.
     * @since 3.4
     */

    /**
     * Identifies the collapse all image in the disabled state.
     * @since 3.4
     */

    /**
     * Identifies the remove image in the enabled state.
     * @since 3.4
     */

    /**
     * Identifies the remove image in the disabled state.
     * @since 3.4
     */

    /**
     * Identifies the remove all image in the enabled state.
     * @since 3.4
     */

    /**
     * Identifies the remove all image in the disabled state.
     * @since 3.4
     */

    /**
     * Identifies the stop image in the enabled state.
     * @since 3.4
     */

    /**
     * Identifies the stop image in the disabled state.
     * @since 3.4
     */

    /**
     * Identifies the synchronize image in the enabled state.
     * @since 3.4
     */

    /**
     * Identifies the synchronize image in the disabled state.
     * @since 3.4
     */

    /**
     * Identifies the clear image in the enabled state.
     * @since 3.4
     */

    /**
     * Identifies the clear image in the disabled state.
     * @since 3.4
     */

    /**
     * Identifies the default perspective image.
     * @since 3.4
     */

    /**
     * Identifies the delete image in the enabled state.
     * @since 3.4
     */

    /**
     * Identifies the delete image in the disabled state.
     * @since 3.4
     */

    /**
     * Identifies the home image in the enabled state.
     * @since 3.4
     */

    /**
     * Identifies the home image in the disabled state.
     * @since 3.4
     */

    /**
     * Identifies the print image in the enabled state.
     * @since 3.4
     */

    /**
     * Identifies the print image in the disabled state.
     * @since 3.4
     */

    /**
     * Identifies the save image in the enabled state.
     * @since 3.4
     */

    /**
     * Identifies the save image in the disabled state.
     * @since 3.4
     */

    /**
     * Identifies the save all image in the enabled state.
     * @since 3.4
     */

    /**
     * Identifies the save all image in the disabled state.
     * @since 3.4
     */

    /**
     * Identifies the save as image in the enabled state.
     * @since 3.4
     */

    /**
     * Identifies the save as image in the disabled state.
     * @since 3.4
     */

    /**
     * Identifies the help image.
     * @since 3.4
     */

    /**
     * Identifies the add image.
     * @since 3.4
     */

    /**
     * Identifies an element image.
     */

    /**
     * Identifies a file image.
     */

    /**
     * Identifies a folder image.
     */

    /**
     * Identifies a project image.
     *
     * @deprecated in 3.0. This image is IDE-specific, and is therefore found
     * only in IDE configurations. IDE-specific tools should use
     * <code>org.eclipse.ui.ide.IDE.SharedImages.IMG_OBJ_PROJECT</code> instead.
     */
    @Deprecated

    /**
     * Identifies a closed project image.
     *
     * @deprecated in 3.0. This image is IDE-specific, and is therefore found
     * only in IDE configurations. IDE-specific tools should use
     * <code>org.eclipse.ui.ide.IDE.SharedImages.IMG_OBJ_PROJECT_CLOSED</code> instead.
     */
    @Deprecated

    /**
     * Identifies the default image used to indicate a bookmark.
     *
     * @deprecated in 3.0. This image is IDE-specific, and is therefore found
     * only in IDE configurations. IDE-specific tools should use
     * <code>org.eclipse.ui.ide.IDE.SharedImages.IMG_OBJS_BKMRK_TSK</code> instead.
     */
    @Deprecated

    /**
     * Identifies the default image used to indicate errors.
     */

    /**
     * Identifies the default image used to indicate information only.
     */

    /**
     * Identifies the default image used to indicate a task.
     *
     * @deprecated in 3.0. This image is IDE-specific, and is therefore found
     * only in IDE configurations. IDE-specific tools should use
     * <code>org.eclipse.ui.ide.IDE.SharedImages.IMG_OBJS_TASK_TSK</code> instead.
     */
    @Deprecated

    /**
     * Identifies the default image used to indicate warnings.
     */

    /**
     * Identifies the image used for "open marker".
     *
     * @deprecated in 3.0. This image is IDE-specific, and is therefore found
     * only in IDE configurations. IDE-specific tools should use
     * <code>org.eclipse.ui.ide.IDE.SharedImages.IMG_OPEN_MARKER</code> instead.
     */
    @Deprecated

    /**
     * Identifies the back image in the enabled state.
     */

    /**
     * Identifies the back image in the disabled state.
     */

    /**
     * Identifies the back image in the hover (colored) state.
     *
     * @deprecated in 3.0. This image is now the same as <code>IMG_TOOL_BACK</code>.
     *   Enabled images are now in color.  The workbench itself no longer uses the hover image variants.
     */
    @Deprecated

    /**
     * Identifies the copy image in the enabled state.
     */

    /**
     * Identifies the copy image in the disabled state.
     */

    /**
     * Identifies the copy image in the hover (colored) state.
     *
     * @deprecated in 3.0. This image is now the same as <code>IMG_TOOL_COPY</code>.
     *   Enabled images are now in color.  The workbench itself no longer uses the hover image variants.
     */
    @Deprecated

    /**
     * Identifies the cut image in the enabled state.
     */

    /**
     * Identifies the cut image in the disabled state.
     */

    /**
     * Identifies the cut image in the hover (colored) state.
     *
     * @deprecated in 3.0. This image is now the same as <code>IMG_TOOL_CUT</code>.
     *   Enabled images are now in color.  The workbench itself no longer uses the hover image variants.
     */
    @Deprecated

    /**
     * Identifies the delete image in the enabled state.
     * @since 3.4
     */

    /**
     * Identifies the delete image in the disabled state.
     * @since 3.4
     */

    /**
     * Identifies the delete image in the hover (colored) state.
     *
     * @deprecated in 3.0. This image is now the same as <code>IMG_TOOL_DELETE</code>.
     *   Enabled images are now in color.  The workbench itself no longer uses the hover image variants.
     */
    @Deprecated

    /**
     * Identifies the forward image in the enabled state.
     */

    /**
     * Identifies the forward image in the disabled state.
     */

    /**
     * Identifies the forward image in the hover (colored) state.
     *
     * @deprecated in 3.0. This image is now the same as <code>IMG_TOOL_FORWARD</code>.
     *   Enabled images are now in color.  The workbench itself no longer uses the hover image variants.
     */
    @Deprecated

    /**
     * Identifies the new wizard image in the enabled state.
     */

    /**
     * Identifies the new wizard image in the disabled state.
     */

    /**
     * Identifies the new wizard image in the hover (colored) state.
     *
     * @deprecated in 3.0. This image is now the same as <code>IMG_TOOL_NEW_WIZARD</code>.
     *   Enabled images are now in color.  The workbench itself no longer uses the hover image variants.
     */
    @Deprecated

    /**
     * Identifies the paste image in the enabled state.
     */

    /**
     * Identifies the paste image in the disabled state.
     */

    /**
     * Identifies the paste image in the hover (colored) state.
     *
     * @deprecated in 3.0. This image is now the same as <code>IMG_TOOL_PASTE</code>.
     *   Enabled images are now in color.  The workbench itself no longer uses the hover image variants.
     */
    @Deprecated

    /**
     * Identifies the redo image in the enabled state.
     */

    /**
     * Identifies the redo image in the disabled state.
     */

    /**
     * Identifies the redo image in the hover (colored) state.
     *
     * @deprecated in 3.0. This image is now the same as <code>IMG_TOOL_REDO</code>.
     *   Enabled images are now in color.  The workbench itself no longer uses the hover image variants.
     */
    @Deprecated

    /**
     * Identifies the undo image in the enabled state.
     */

    /**
     * Identifies the undo image in the disabled state.
     */

    /**
     * Identifies the undo image in the hover (colored) state.
     *
     * @deprecated in 3.0. This image is now the same as <code>IMG_TOOL_UNDO</code>.
     *   Enabled images are now in color.  The workbench itself no longer uses the hover image variants.
     */
    @Deprecated

    /**
     * Identifies the up image in the enabled state.
     */

    /**
     * Identifies the up image in the disabled state.
     */

    /**
     * Identifies the up image in the hover (colored) state.
     *
     * @deprecated in 3.0. This image is now the same as <code>IMG_TOOL_UP</code>.
     *   Enabled images are now in color.  The workbench itself no longer uses the hover image variants.
     */
    @Deprecated


    /**
     * Cursor 'source' for the left arrow cursor. For cursor construction see:
     * @see Cursor#Cursor(Device, ImageData, ImageData, int, int)
     * @since 3.5
     */
    /**
     * Cursor 'mask' for the left arrow cursor. For cursor construction see:
     * @see Cursor#Cursor(Device, ImageData, ImageData, int, int)
     * @since 3.5
     */

    /**
     * Cursor 'source' for the right arrow cursor. For cursor construction see:
     * @see Cursor#Cursor(Device, ImageData, ImageData, int, int)
     * @since 3.5
     */
    /**
     * Cursor 'mask' for the right arrow cursor. For cursor construction see:
     * @see Cursor#Cursor(Device, ImageData, ImageData, int, int)
     * @since 3.5
     */

    /**
     * Cursor 'source' for the up arrow cursor. For cursor construction see:
     * @see Cursor#Cursor(Device, ImageData, ImageData, int, int)
     * @since 3.5
     */
    /**
     * Cursor 'mask' for the up arrow cursor. For cursor construction see:
     * @see Cursor#Cursor(Device, ImageData, ImageData, int, int)
     * @since 3.5
     */

    /**
     * Cursor 'source' for the down arrow cursor. For cursor construction see:
     * @see Cursor#Cursor(Device, ImageData, ImageData, int, int)
     * @since 3.5
     */
    /**
     * Cursor 'mask' for the down arrow cursor. For cursor construction see:
     * @see Cursor#Cursor(Device, ImageData, ImageData, int, int)
     * @since 3.5
     */

    /**
     * Cursor 'source' for the 'no drop' arrow cursor. For cursor construction see:
     * @see Cursor#Cursor(Device, ImageData, ImageData, int, int)
     * @since 3.5
     */
    /**
     * Cursor 'mask' for the 'no drop' arrow cursor. For cursor construction see:
     * @see Cursor#Cursor(Device, ImageData, ImageData, int, int)
     * @since 3.5
     */

    /**
     * Cursor 'source' for the 'in stack' arrow cursor. For cursor construction see:
     * @see Cursor#Cursor(Device, ImageData, ImageData, int, int)
     * @since 3.5
     */
    /**
     * Cursor 'mask' for the 'in stack' arrow cursor. For cursor construction see:
     * @see Cursor#Cursor(Device, ImageData, ImageData, int, int)
     * @since 3.5
     */

    /**
     * Cursor 'source' for the 'off-screen' (detached window) arrow cursor. For cursor construction see:
     * @see Cursor#Cursor(Device, ImageData, ImageData, int, int)
     * @since 3.5
     */
    /**
     * Cursor 'mask' for the 'off-screen' (detached window) arrow cursor. For cursor construction see:
     * @see Cursor#Cursor(Device, ImageData, ImageData, int, int)
     * @since 3.5
     */

    /**
     * Cursor 'source' for the 'fast-view' arrow cursor. For cursor construction see:
     * @see Cursor#Cursor(Device, ImageData, ImageData, int, int)
     * @since 3.5
     */
    /**
     * Cursor 'mask' for the 'fast-view' arrow cursor. For cursor construction see:
     * @see Cursor#Cursor(Device, ImageData, ImageData, int, int)
     * @since 3.5
     */

    /**
     * Retrieves the specified image from the workbench plugin's image registry.
     * Note: The returned <code>Image</code> is managed by the workbench; clients
     * must <b>not</b> dispose of the returned image.
     *
     * @param symbolicName the symbolic name of the image; there are constants
     * declared in this interface for build-in images that come with the workbench
     * @return the image, or <code>null</code> if not found
     */
    public Image getImage(String symbolicName);

    /**
     * Retrieves the image descriptor for specified image from the workbench's
     * image registry. Unlike <code>Image</code>s, image descriptors themselves do
     * not need to be disposed.
     *
     * @param symbolicName the symbolic name of the image; there are constants
     * declared in this interface for build-in images that come with the workbench
     * @return the image descriptor, or <code>null</code> if not found
     */
    public ImageDescriptor getImageDescriptor(String symbolicName);
}
