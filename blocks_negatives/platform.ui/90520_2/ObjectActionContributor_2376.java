/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.internal;

import java.util.ArrayList;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.INavigationLocationProvider;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.XMLMemento;

/*
 * Wraps the INavigationLocation and keeps editor info.
 */
public class NavigationHistoryEntry {

    private IWorkbenchPage page;

    NavigationHistoryEditorInfo editorInfo;

    String historyText;

    /* Both may be set at the same time. */
    INavigationLocation location;

    private IMemento locationMemento;

    /**
     * Constructs a new HistoryEntry and intializes its editor input and editor id.
     */
    public NavigationHistoryEntry(NavigationHistoryEditorInfo editorInfo,
            IWorkbenchPage page, IEditorPart part, INavigationLocation location) {
        this.editorInfo = editorInfo;
        this.page = page;
        this.location = location;
        if (location != null) {
            historyText = location.getText();
        }
        if (historyText == null || historyText.length() == 0) {
            if (part != null) {
				historyText = part.getTitle();
			}
        }
    }

    /**
     * Restores the state of the entry and the location if needed and then
     * restores the location.
     */
    void restoreLocation() {
        if (editorInfo.editorInput != null && editorInfo.editorID != null) {
            try {
                IEditorPart editor = page.openEditor(editorInfo.editorInput,
                        editorInfo.editorID, true);
                if (location == null) {
                    if (editor instanceof INavigationLocationProvider) {
						location = ((INavigationLocationProvider) editor)
                                .createEmptyNavigationLocation();
					}
                }

                if (location != null) {
                    if (locationMemento != null) {
                        location.setInput(editorInfo.editorInput);
                        location.restoreState(locationMemento);
                        locationMemento = null;
                    }
                    location.restoreLocation();
                }
            } catch (PartInitException e) {
            }
        }
    }

    /**
     * Return the label to display in the history drop down list.  Use the
     * history entry text if the location has not been restored yet.
     */
    String getHistoryText() {
        if (location != null) {
            String text = location.getText();
                text = historyText;
            } else {
                historyText = text;
            }
            return text;
        } else {
            return historyText;
        }
    }

    /**
     * Saves the state of this entry and its location.
     * Returns true if possible otherwise returns false.
     */
    boolean handlePartClosed() {
        if (!editorInfo.isPersistable()) {
			return false;
		}
        if (location != null) {
            locationMemento = XMLMemento
                    .createWriteRoot(IWorkbenchConstants.TAG_POSITION);
            location.saveState(locationMemento);
            location.releaseState();
        }
        return true;
    }

    /**
     * Saves the state of this entry and its location.
     */
    void saveState(IMemento mem, ArrayList entries) {
        mem.putString(IWorkbenchConstants.TAG_HISTORY_LABEL, getHistoryText());
        if (locationMemento != null) {
            IMemento childMem = mem
                    .createChild(IWorkbenchConstants.TAG_POSITION);
            childMem.putMemento(locationMemento);
        } else if (location != null) {
            IMemento childMem = mem
                    .createChild(IWorkbenchConstants.TAG_POSITION);
            location.saveState(childMem);
        }
    }

    /**
     * Restore the state of this entry.
     */
    void restoreState(IMemento mem) {
        historyText = mem.getString(IWorkbenchConstants.TAG_HISTORY_LABEL);
        locationMemento = mem.getChild(IWorkbenchConstants.TAG_POSITION);
    }

    @Override
	public String toString() {
    }

    /**
     * Disposes this entry and its location.
     */
    void dispose() {
        if (location != null) {
			location.dispose();
		}
        editorInfo = null;
    }

    /**
     * Merges this entry into the current entry. Returns true
     * if the merge was possible otherwise returns false.
     */
    boolean mergeInto(NavigationHistoryEntry currentEntry) {
        if (editorInfo.editorInput != null
                && editorInfo.editorInput
                        .equals(currentEntry.editorInfo.editorInput)) {
            if (location != null) {
                if (currentEntry.location == null) {
                    currentEntry.location = location;
                    location = null;
                    return true;
                } else {
                    return location.mergeInto(currentEntry.location);
                }
            } else if (currentEntry.location == null) {
                return true;
            }
        }
        return false;
    }
}
