/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.views.navigator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.ReadOnlyStateChecker;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.views.navigator.ResourceNavigatorMessages;
import org.eclipse.ui.part.ResourceTransfer;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * Implements drag behaviour when items are dragged out of the
 * resource navigator.
 *
 * @since 2.0
 * @deprecated as of 3.5, use the Common Navigator Framework classes instead
 */
@Deprecated
public class NavigatorDragAdapter extends DragSourceAdapter {
    private static final String CHECK_MOVE_TITLE = ResourceNavigatorMessages.DragAdapter_title;

    private static final String CHECK_DELETE_MESSAGE = ResourceNavigatorMessages.DragAdapter_checkDeleteMessage;

    ISelectionProvider selectionProvider;

    private TransferData lastDataType;

    /**
     * Constructs a new drag adapter.
     * @param provider The selection provider
     */
    public NavigatorDragAdapter(ISelectionProvider provider) {
        selectionProvider = provider;
    }

    /**
     * This implementation of {@link DragSourceListener#dragFinished(DragSourceEvent)}
     * responds to a drag that has moved resources outside the Navigator by deleting
     * the corresponding source resource.
     */
    @Override
	public void dragFinished(DragSourceEvent event) {
        LocalSelectionTransfer.getInstance().setSelection(null);

        if (event.doit == false) {
			return;
		}

        final int typeMask = IResource.FOLDER | IResource.FILE;
        if (event.detail == DND.DROP_MOVE) {
            if (lastDataType != null
                    && FileTransfer.getInstance().isSupportedType(lastDataType)) {
				return;
			}

            IResource[] resources = getSelectedResources(typeMask);
            DragSource dragSource = (DragSource) event.widget;
            Control control = dragSource.getControl();
            Shell shell = control.getShell();
            ReadOnlyStateChecker checker;

            if (resources == null || resources.length == 0) {
				return;
			}

            checker = new ReadOnlyStateChecker(shell, CHECK_MOVE_TITLE,
                    CHECK_DELETE_MESSAGE);
            resources = checker.checkReadOnlyResources(resources);
            for (int i = 0; i < resources.length; i++) {
                try {
                    resources[i].delete(IResource.KEEP_HISTORY
                            | IResource.FORCE, null);
                } catch (CoreException e) {
                    StatusManager.getManager().handle(e, IDEWorkbenchPlugin.IDE_WORKBENCH);
                }
            }
        } else if (event.detail == DND.DROP_TARGET_MOVE) {
            IResource[] resources = getSelectedResources(typeMask);

            if (resources == null) {
				return;
			}
            for (int i = 0; i < resources.length; i++) {
                try {
                    resources[i].refreshLocal(IResource.DEPTH_INFINITE, null);
                } catch (CoreException e) {
                	 StatusManager.getManager().handle(e, IDEWorkbenchPlugin.IDE_WORKBENCH);
                }
            }
        }
    }

    /**
     * This implementation of {@link DragSourceListener#dragSetData(DragSourceEvent)}
     * sets the drag event data based on the current selection in the Navigator.
     */
    @Override
	public void dragSetData(DragSourceEvent event) {
        final int typeMask = IResource.FILE | IResource.FOLDER;
        IResource[] resources = getSelectedResources(typeMask);

        if (resources == null || resources.length == 0) {
			return;
		}

        lastDataType = event.dataType;
        if (LocalSelectionTransfer.getInstance()
                .isSupportedType(event.dataType)) {
            event.data = LocalSelectionTransfer.getInstance().getSelection();
            return;
        }
        if (ResourceTransfer.getInstance().isSupportedType(event.dataType)) {
            event.data = resources;
            return;
        }
        if (!FileTransfer.getInstance().isSupportedType(event.dataType)) {
			return;
		}

        final int length = resources.length;
        int actualLength = 0;
        String[] fileNames = new String[length];
        for (int i = 0; i < length; i++) {
            IPath location = resources[i].getLocation();
            if (location != null) {
				fileNames[actualLength++] = location.toOSString();
			}
        }
        if (actualLength == 0) {
			return;
		}
        if (actualLength < length) {
            String[] tempFileNames = fileNames;
            fileNames = new String[actualLength];
            for (int i = 0; i < actualLength; i++) {
				fileNames[i] = tempFileNames[i];
			}
        }
        event.data = fileNames;
    }

    /**
     * This implementation of {@link DragSourceListener#dragStart(DragSourceEvent)}
     * allows the drag to start if the current Navigator selection contains resources
     * that can be dragged.
     */
    @Override
	public void dragStart(DragSourceEvent event) {
        lastDataType = null;
        DragSource dragSource = (DragSource) event.widget;
        Control control = dragSource.getControl();
        if (control != control.getDisplay().getFocusControl()) {
            event.doit = false;
            return;
        }

        IStructuredSelection selection = (IStructuredSelection) selectionProvider
                .getSelection();
        for (Iterator i = selection.iterator(); i.hasNext();) {
            Object next = i.next();
            if (!(next instanceof IFile || next instanceof IFolder)) {
                event.doit = false;
                return;
            }
        }
        if (selection.isEmpty()) {
            event.doit = false;
            return;
        }
        LocalSelectionTransfer.getInstance().setSelection(selection);
        event.doit = true;
    }

    private IResource[] getSelectedResources(int resourceTypes) {
        List resources = new ArrayList();
        IResource[] result = new IResource[0];

        ISelection selection = selectionProvider.getSelection();
        if (!(selection instanceof IStructuredSelection) || selection.isEmpty()) {
            return null;
        }
        IStructuredSelection structuredSelection = (IStructuredSelection) selection;

        Iterator itr = structuredSelection.iterator();
        while (itr.hasNext()) {
            Object obj = itr.next();
            if (obj instanceof IResource) {
                IResource res = (IResource) obj;
                if ((res.getType() & resourceTypes) == res.getType()) {
                    resources.add(res);
                }
            }
        }
        result = new IResource[resources.size()];
        resources.toArray(result);
        return result;
    }
}
