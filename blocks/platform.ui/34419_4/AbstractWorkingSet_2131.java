package org.eclipse.ui.internal;

import java.util.Hashtable;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.INullSelectionListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;

public abstract class AbstractSelectionService implements ISelectionService {

    private ListenerList listeners = new ListenerList();

    private ListenerList postListeners = new ListenerList();

    private IWorkbenchPart activePart;

    private ISelectionProvider activeProvider;

    private Hashtable perPartTrackers;

    private ISelectionChangedListener selListener = new ISelectionChangedListener() {
        @Override
		public void selectionChanged(SelectionChangedEvent event) {
            fireSelection(activePart, event.getSelection());
        }
    };

    private ISelectionChangedListener postSelListener = new ISelectionChangedListener() {
        @Override
		public void selectionChanged(SelectionChangedEvent event) {
            firePostSelection(activePart, event.getSelection());
        }
    };

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

    protected void fireSelection(final IWorkbenchPart part, final ISelection sel) {
        Object[] array = listeners.getListeners();
        for (int i = 0; i < array.length; i++) {
            final ISelectionListener l = (ISelectionListener) array[i];
            if ((part != null && sel != null)
                    || l instanceof INullSelectionListener) {
                
                try {
                    l.selectionChanged(part, sel);
                } catch (Exception e) {
                    WorkbenchPlugin.log(e);
                }
            }
        }
    }

    protected void firePostSelection(final IWorkbenchPart part,
            final ISelection sel) {
        Object[] array = postListeners.getListeners();
        for (int i = 0; i < array.length; i++) {
            final ISelectionListener l = (ISelectionListener) array[i];
            if ((part != null && sel != null)
                    || l instanceof INullSelectionListener) {
                
                try {
                    l.selectionChanged(part, sel);
                } catch (Exception e) {
                    WorkbenchPlugin.log(e);
                }
            }
        }
    }

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

    protected abstract AbstractPartSelectionTracker createPartTracker(
            String partId);

    @Override
	public ISelection getSelection() {
        if (activeProvider != null) {
			return activeProvider.getSelection();
		} else {
			return null;
		}
    }

    @Override
	public ISelection getSelection(String partId) {
        return getPerPartTracker(partId).getSelection();
    }

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
