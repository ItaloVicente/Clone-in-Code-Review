
package org.eclipse.ui.internal;

import java.util.ArrayList;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.INavigationLocationProvider;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.XMLMemento;

public class NavigationHistoryEntry {

    private IWorkbenchPage page;

    NavigationHistoryEditorInfo editorInfo;

    String historyText;

    INavigationLocation location;

    private IMemento locationMemento;

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

    String getHistoryText() {
        if (location != null) {
            String text = location.getText();
            if ((text == null) || text.equals("")) { //$NON-NLS-1$
                text = historyText;
            } else {
                historyText = text;
            }
            return text;
        } else {
            return historyText;
        }
    }

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

    void restoreState(IMemento mem) {
        historyText = mem.getString(IWorkbenchConstants.TAG_HISTORY_LABEL);
        locationMemento = mem.getChild(IWorkbenchConstants.TAG_POSITION);
    }

    @Override
	public String toString() {
        return "Input<" + editorInfo.editorInput + "> Details<" + location + ">"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    void dispose() {
        if (location != null) {
			location.dispose();
		}
        editorInfo = null;
    }

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
