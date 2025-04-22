/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal;

import java.util.Hashtable;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.INullSelectionListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Abstract selection service.
 */
public abstract class AbstractSelectionService implements ISelectionService {

    /**
     * The list of selection listeners (not per-part).
     */
	private ListenerList<ISelectionListener> listeners = new ListenerList<>();

    /**
     * The list of post selection listeners (not per-part).
     */
	private ListenerList<ISelectionListener> postListeners = new ListenerList<>();

    /**
     * The currently active part.
     */
    private IWorkbenchPart activePart;

    /**
     * The active part's selection provider, remembered in case the part
     * replaces its selection provider after we hooked a listener.
     */
    private ISelectionProvider activeProvider;

    /**
     * Map from part id (String) to per-part tracker (AbstractPartSelectionTracker).
     */
    private Hashtable perPartTrackers;

    /**
     * The JFace selection listener to hook on the active part's selection provider.
     */
    private ISelectionChangedListener selListener = event -> fireSelection(activePart, event.getSelection());

    /**
     * The JFace post selection listener to hook on the active part's selection provider.
     */
    private ISelectionChangedListener postSelListener = event -> firePostSelection(activePart, event.getSelection());

    /**
     * Creates a new SelectionService.
     */
    protected AbstractSelectionService() {
    }

    @Override
	public void addSelectionListener(ISelectionListener l) {
        listeners.add(l);
    }

    @Override
	public void addSelectionListener(String partId, ISelectionListener listener) {
        getPerPartTracker(partId).addSelectionListener(listener);
    }

    @Override
	public void addPostSelectionListener(ISelectionListener l) {
        postListeners.add(l);
    }

    @Override
	public void addPostSelectionListener(String partId,
            ISelectionListener listener) {
        getPerPartTracker(partId).addPostSelectionListener(listener);
    }

    @Override
	public void removeSelectionListener(ISelectionListener l) {
        listeners.remove(l);
    }

    @Override
	public void removePostSelectionListener(String partId,
            ISelectionListener listener) {
        getPerPartTracker(partId).removePostSelectionListener(listener);
    }

    @Override
	public void removePostSelectionListener(ISelectionListener l) {
        postListeners.remove(l);
    }

    @Override
	public void removeSelectionListener(String partId,
            ISelectionListener listener) {
        getPerPartTracker(partId).removeSelectionListener(listener);
    }

    /**
     * Fires a selection event to the given listeners.
     *
     * @param part the part or <code>null</code> if no active part
     * @param sel the selection or <code>null</code> if no active selection
     */
    protected void fireSelection(final IWorkbenchPart part, final ISelection sel) {
		for (final ISelectionListener l : listeners) {
			if ((part != null && sel != null) || l instanceof INullSelectionListener) {
                try {
                    l.selectionChanged(part, sel);
                } catch (Exception e) {
                    WorkbenchPlugin.log(e);
                }
            }
        }
    }

    /**
     * Fires a selection event to the given listeners.
     *
     * @param part the part or <code>null</code> if no active part
     * @param sel the selection or <code>null</code> if no active selection
     */
    protected void firePostSelection(final IWorkbenchPart part,
            final ISelection sel) {
		for (final ISelectionListener l : postListeners) {
			if ((part != null && sel != null) || l instanceof INullSelectionListener) {
                try {
                    l.selectionChanged(part, sel);
                } catch (Exception e) {
                    WorkbenchPlugin.log(e);
                }
            }
        }
    }

    /**
     * Returns the per-part selection tracker for the given part id.
     *
     * @param partId part identifier
     * @return per-part selection tracker
     */
    protected AbstractPartSelectionTracker getPerPartTracker(String partId) {
        if (perPartTrackers == null) {
            perPartTrackers = new Hashtable(4);
        }
        AbstractPartSelectionTracker tracker = (AbstractPartSelectionTracker) perPartTrackers
                .get(partId);
        if (tracker == null) {
            tracker = createPartTracker(partId);
            perPartTrackers.put(partId, tracker);
        }
        return tracker;
    }

    /**
     * Creates a new per-part selection tracker for the given part id.
     *
     * @param partId part identifier
     * @return per-part selection tracker
     */
    protected abstract AbstractPartSelectionTracker createPartTracker(
            String partId);

    /**
     * Returns the selection.
     */
    @Override
	public ISelection getSelection() {
        if (activeProvider != null) {
			return activeProvider.getSelection();
		}
		return null;
    }

    @Override
	public ISelection getSelection(String partId) {
        return getPerPartTracker(partId).getSelection();
    }

    /**
     * Sets the current-active part (or null if none)
     *
     * @since 3.1
     *
     * @param newPart the new active part (or null if none)
     */
    public void setActivePart(IWorkbenchPart newPart) {
        if (newPart == activePart) {
			return;
		}

        ISelectionProvider selectionProvider = null;

        if (newPart != null) {
            selectionProvider = newPart.getSite().getSelectionProvider();

            if (selectionProvider == null) {
                newPart = null;
            }
        }

        if (newPart == activePart) {
			return;
		}

        if (activePart != null) {
            if (activeProvider != null) {
                activeProvider.removeSelectionChangedListener(selListener);
                if (activeProvider instanceof IPostSelectionProvider) {
					((IPostSelectionProvider) activeProvider)
                            .removePostSelectionChangedListener(postSelListener);
				} else {
					activeProvider
                            .removeSelectionChangedListener(postSelListener);
				}
                activeProvider = null;
            }
            activePart = null;
        }

        activePart = newPart;

        if (newPart != null) {
            activeProvider = selectionProvider;
            activeProvider.addSelectionChangedListener(selListener);
            ISelection sel = activeProvider.getSelection();
            fireSelection(newPart, sel);
            if (activeProvider instanceof IPostSelectionProvider) {
				((IPostSelectionProvider) activeProvider)
                        .addPostSelectionChangedListener(postSelListener);
			} else {
				activeProvider.addSelectionChangedListener(postSelListener);
			}
            firePostSelection(newPart, sel);
        } else {
            fireSelection(null, null);
            firePostSelection(null, null);
        }
    }


}
