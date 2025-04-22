/*******************************************************************************
 * Copyright (c) 2005, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.misc;

import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.util.Util;


/**
 * To whom it may concern: Please stop deleting the logging code. This is important for debugging
 * event ordering issues.
 */
public class UIListenerLogging {


    public final static boolean enabled = internal_isEnabled(LISTENER_EVENTS);





    private static String getSourceId(Object source) {
        return Util.safeString(Integer.toString(source.hashCode() % 1000));
    }

    private static String getWindowId(IWorkbenchWindow source) {
    }

    private static String getPageId(IWorkbenchPage page) {
    }

    private static String getPerspectiveId(IPerspectiveDescriptor descriptor) {
        return Util.safeString(descriptor.getId());
    }

    public static final void logPageEvent(IWorkbenchWindow window, IWorkbenchPage page, String eventId) {
        if (isEnabled(WINDOW_PAGE_EVENTS)) {
            System.out.println(WINDOW_PAGE_EVENTS
        }
    }

    public static final void logPerspectiveEvent(IWorkbenchWindow window, IWorkbenchPage page,
            IPerspectiveDescriptor descriptor, String eventId) {

        if (isEnabled(WINDOW_PERSPECTIVE_EVENTS)) {
            System.out.println(WINDOW_PERSPECTIVE_EVENTS
                    + " " + eventId + " (" + getPageId(page) + ", " + getPerspectiveId(descriptor) + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        }
    }

    public static final void logPerspectiveChangedEvent(IWorkbenchWindow window, IWorkbenchPage page,
            IPerspectiveDescriptor descriptor, IWorkbenchPartReference ref, String changeId) {

        if (isEnabled(WINDOW_PERSPECTIVE_EVENTS)) {
            System.out.println(WINDOW_PERSPECTIVE_EVENTS
                    + " perspectiveChanged (" + getPageId(page) + ", " + getPerspectiveId(descriptor)  //$NON-NLS-1$ //$NON-NLS-2$
                    + ", " + getPartId(ref) + ", " + changeId + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        }
    }

    public static final void logPerspectiveSavedAs(IWorkbenchWindow window, IWorkbenchPage page,
    		IPerspectiveDescriptor oldDescriptor, IPerspectiveDescriptor newDescriptor) {

        if (isEnabled(WINDOW_PERSPECTIVE_EVENTS)) {
            System.out.println(WINDOW_PERSPECTIVE_EVENTS
                    + " " + PLE_PERSP_SAVED_AS + " (" + getPageId(page) + ", " + getPerspectiveId(oldDescriptor)  + ", " + getPerspectiveId(newDescriptor) + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        }
    }


    private static String getPartId(IWorkbenchPart part) {
        if (part == null) {
        }

            + Util.safeString(part.getSite().getId());
    }

    private static String getPartId(IWorkbenchPartReference ref) {
        if (ref == null) {
        }

            + Util.safeString(ref.getId());
    }

    /**
     * Log a partListener event fired from the workbench window
     *
     * @param page
     * @param eventId
     */
    public static final void logPartListenerEvent(String sourceType, Object source, IWorkbenchPart part, String eventId) {
        if (isEnabled(sourceType)) {
                    + ", " + eventId + "(" + getPartId(part) + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }
    }

    /**
     * Log a partListener2 event fired from the workbench window
     *
     * @param page
     * @param eventId
     */
    public static final void logPartListener2Event(String sourceType, Object source, IWorkbenchPartReference part, String eventId) {
        if (isEnabled(sourceType)) {
                    + ", " + eventId + "(" + getPartId(part) + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }
    }

    /**
     * Log an event fired from the workbench page
     *
     * @param page
     * @param eventId
     */
    public static final void logPartListenerEvent(IWorkbenchPage page, IWorkbenchPart part, String eventId) {
        if (isEnabled(PAGE_PARTLISTENER_EVENTS)) {
                    + Util.safeString(page.getLabel())
                    + ", " + eventId + "(" + getPartId(part) + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }
    }

    /**
     * Log an event fired from the workbench page
     *
     * @param page
     * @param eventId
     */
    public static final void logPartListener2Event(IWorkbenchPage page, IWorkbenchPartReference part, String eventId) {
        if (isEnabled(PAGE_PARTLISTENER2_EVENTS)) {
                    + Util.safeString(page.getLabel())
                    + ", " + eventId + "(" + getPartId(part) + ")");          //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }
    }

    /**
     * Log an event fired from the workbench page
     *
     * @param page
     * @param eventId
     */
    public static final void logPagePropertyChanged(IWorkbenchPage page, String changeId, Object oldValue, Object newValue) {
        if (isEnabled(PAGE_PROPERTY_EVENTS)) {
                    + Util.safeString(page.getLabel())
                    + ", " + changeId + " = " + Util.safeString(newValue.toString()) + "( old value = " + newValue.toString() + " )"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        }
    }

    public static final void logPartReferencePropertyChange(IWorkbenchPartReference ref, int changeId) {
        if (isEnabled(PARTREFERENCE_PROPERTY_EVENTS)) {
            String eventDescription;

            switch(changeId) {
                default:
            }

                    + getPartId(ref)
                    + ", property " + eventDescription);         //$NON-NLS-1$
        }
    }

    private static boolean isEnabled(String eventName) {
        return enabled && internal_isEnabled(eventName);
    }

    private static boolean internal_isEnabled(String eventName) {
        String option = Platform.getDebugOption(eventName);
    }
}
