package org.eclipse.ui.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.e4.ui.workbench.renderers.swt.SWTPartRenderer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.ISaveablesLifecycleListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.ISizeProvider;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPart2;
import org.eclipse.ui.IWorkbenchPart3;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.e4.compatibility.CompatibilityPart;
import org.eclipse.ui.internal.misc.UIListenerLogging;
import org.eclipse.ui.internal.util.Util;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public abstract class WorkbenchPartReference implements IWorkbenchPartReference, ISizeProvider {

    public static final int INTERNAL_PROPERTY_OPENED = 0x211;
    
    public static final int INTERNAL_PROPERTY_CLOSED = 0x212;

    public static final int INTERNAL_PROPERTY_PINNED = 0x213;

    public static final int INTERNAL_PROPERTY_VISIBLE = 0x214;

    public static final int INTERNAL_PROPERTY_ZOOMED = 0x215;

    public static final int INTERNAL_PROPERTY_ACTIVE_CHILD_CHANGED = 0x216;

    public static final int INTERNAL_PROPERTY_MAXIMIZED = 0x217;

    
    public static int STATE_LAZY = 0;
     
    public static int STATE_CREATION_IN_PROGRESS = 1;
    
    public static int STATE_CREATED = 2;
    
    public static int STATE_DISPOSED = 3;

	static String MEMENTO_KEY = "memento"; //$NON-NLS-1$
  
    private int state = STATE_LAZY;
   
	protected IWorkbenchPart legacyPart;
    private boolean pinned = false;
    
    private ListenerList propChangeListeners = new ListenerList();

    private ListenerList internalPropChangeListeners = new ListenerList();
    
    private ListenerList partChangeListeners = new ListenerList();
    
    protected Map propertyCache = new HashMap();
    
    private IPropertyListener propertyChangeListener = new IPropertyListener() {
        @Override
		public void propertyChanged(Object source, int propId) {
            partPropertyChanged(source, propId);
        }
    };
    
    private IPropertyChangeListener partPropertyChangeListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			partPropertyChanged(event);
		}
    };

	private IWorkbenchPage page;

	private MPart part;

	private IEclipseContext windowContext;

	private EventHandler contextEventHandler;
    
    public WorkbenchPartReference(IEclipseContext windowContext, IWorkbenchPage page, MPart part) {
    	this.windowContext = windowContext;
		this.page = page;
		this.part = part;

		if (part != null) {
			part.getTransientData().put(IWorkbenchPartReference.class.getName(), this);
		}
	}

	private EventHandler createContextEventHandler() {
		if (contextEventHandler == null) {
			contextEventHandler = new EventHandler() {
				@Override
				public void handleEvent(Event event) {
					Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
					MPart part = getModel();
					if (element == part) {
						if (part.getContext() != null) {
							part.getContext().set(getClass().getName(), this);
							unsubscribe();
						}
					}
				}
			};
		}
		return contextEventHandler;
	}

	public void subscribe() {
		IEventBroker broker = windowContext.get(IEventBroker.class);
		broker.subscribe(UIEvents.Context.TOPIC_CONTEXT,
				createContextEventHandler());
	}

	public void unsubscribe() {
		if (contextEventHandler != null) {
			IEventBroker broker = windowContext.get(IEventBroker.class);
			broker.unsubscribe(contextEventHandler);
			contextEventHandler = null;
		}
	}
    
    public boolean isDisposed() {
        return state == STATE_DISPOSED;
    }
    
    protected void checkReference() {
        if (state == STATE_DISPOSED) {
            throw new RuntimeException("Error: IWorkbenchPartReference disposed"); //$NON-NLS-1$
        }
    }
    
	public MPart getModel() {
		return part;
	}


    protected void partPropertyChanged(Object source, int propId) {
		firePropertyChange(propId);
        
        if (propId == IWorkbenchPartConstants.PROP_DIRTY) {
        	IWorkbenchPart actualPart = getPart(false);
        	if (actualPart != null) {
				SaveablesList modelManager = (SaveablesList) actualPart.getSite().getService(ISaveablesLifecycleListener.class);
	        	modelManager.dirtyChanged(actualPart);
        	}
        }
    }
    
    protected void partPropertyChanged(PropertyChangeEvent event) {
    	firePartPropertyChange(event);
    }

    protected void releaseReferences() {

    }

        internalPropChangeListeners.add(listener);
    }
    
        internalPropChangeListeners.remove(listener);
    }

    protected void fireInternalPropertyChange(int id) {
        Object listeners[] = internalPropChangeListeners.getListeners();
        for (int i = 0; i < listeners.length; i++) {
            ((IPropertyListener) listeners[i]).propertyChanged(this, id);
        }
    }
    
    @Override
	public void addPropertyListener(IPropertyListener listener) {
        if (isDisposed()) {
            return;
        }
        
        propChangeListeners.add(listener);
    }

    @Override
	public void removePropertyListener(IPropertyListener listener) {
        if (isDisposed()) {
            return;
        }
        propChangeListeners.remove(listener);
    }


	@Override
	public String getTitle() {
		String title = legacyPart == null ? part.getLocalizedLabel() : legacyPart.getTitle();
		return Util.safeString(title);
	}

	@Override
	public String getTitleToolTip() {
		String toolTip = (String) part.getTransientData().get(
				IPresentationEngine.OVERRIDE_TITLE_TOOL_TIP_KEY);
		if (toolTip == null || toolTip.length() == 0)
			toolTip = part.getLocalizedTooltip();
		return Util.safeString(toolTip);
	}

	@Override
	public String getId() {
		String id = part.getElementId();

		int colonIndex = id.indexOf(':');
		return colonIndex == -1 ? id : id.substring(0, colonIndex);
	}

    protected String computeTitle() {
        return getRawTitle();
    }

    protected final String getRawTitle() {
		return Util.safeString(legacyPart.getTitle());
    }

	@Override
	public final Image getTitleImage() {
		if (isDisposed()) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_DEF_VIEW);
		}

		WorkbenchWindow wbw = (WorkbenchWindow) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (part != null && wbw.getModel().getRenderer() instanceof SWTPartRenderer) {
			SWTPartRenderer r = (SWTPartRenderer) wbw.getModel().getRenderer();
			return r.getImage(part);
		}

		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_DEF_VIEW);
	}
    
        fireInternalPropertyChange(INTERNAL_PROPERTY_VISIBLE);
    }

        fireInternalPropertyChange(INTERNAL_PROPERTY_ZOOMED);
    }

	protected void firePropertyChange(int id) {
        immediateFirePropertyChange(id);
    }
    
    private void immediateFirePropertyChange(int id) {
        UIListenerLogging.logPartReferencePropertyChange(this, id);
        Object listeners[] = propChangeListeners.getListeners();
        for (int i = 0; i < listeners.length; i++) {
			((IPropertyListener) listeners[i]).propertyChanged(legacyPart, id);
        }
        
        fireInternalPropertyChange(id);
    }

	public abstract PartSite getSite();

	public abstract void initialize(IWorkbenchPart part) throws PartInitException;

	void addPropertyListeners() {
		IWorkbenchPart workbenchPart = getPart(false);
		if (workbenchPart != null) {
			workbenchPart.addPropertyListener(propertyChangeListener);

			if (workbenchPart instanceof IWorkbenchPart3) {
				((IWorkbenchPart3) workbenchPart)
						.addPartPropertyListener(partPropertyChangeListener);

			}
		}
	}

    @Override
	public final IWorkbenchPart getPart(boolean restore) {
        if (isDisposed()) {
            return null;
        }
        
        if (legacyPart == null) {
			if (restore && part.getWidget() == null) {
				EPartService partService = windowContext.get(EPartService.class);
				partService.showPart(part, PartState.CREATE);
			}

			if (part.getObject() instanceof CompatibilityPart) {
				CompatibilityPart compatibilityPart = (CompatibilityPart) part.getObject();
				if (compatibilityPart != null) {
					legacyPart = compatibilityPart.getPart();
				}
			} else if (part.getObject() != null) {
        		if (part.getTransientData().get(E4PartWrapper.E4_WRAPPER_KEY) instanceof E4PartWrapper) {
        		  return (IWorkbenchPart) part.getTransientData().get(E4PartWrapper.E4_WRAPPER_KEY);
				}
        	}
		}

		return legacyPart;
    }
    
	public abstract IWorkbenchPart createPart() throws PartInitException;

	abstract IWorkbenchPart createErrorPart();

	public abstract IWorkbenchPart createErrorPart(IStatus status);

	protected void doDisposeNestedParts() {
	}

	private void doDisposePart() {
		if (legacyPart != null) {
            fireInternalPropertyChange(INTERNAL_PROPERTY_CLOSED);
            try {
				legacyPart.removePropertyListener(propertyChangeListener);
				if (legacyPart instanceof IWorkbenchPart3) {
					((IWorkbenchPart3) legacyPart)
							.removePartPropertyListener(partPropertyChangeListener);
                }
            } catch (Exception e) {
                WorkbenchPlugin.log(e);
            }
			legacyPart = null;
        }
    }

    public void setPinned(boolean newPinned) {
        if (isDisposed()) {
            return;
        }

        if (newPinned == pinned) {
            return;
        }
        
        pinned = newPinned;

		immediateFirePropertyChange(IWorkbenchPartConstants.PROP_TITLE);
        if (pinned)
        	part.getTags().add(IPresentationEngine.ADORNMENT_PIN);
        else
        	part.getTags().remove(IPresentationEngine.ADORNMENT_PIN);

        fireInternalPropertyChange(INTERNAL_PROPERTY_PINNED);
    }
    
    public boolean isPinned() {
        return pinned;
    }

    @Override
	public String getPartProperty(String key) {
		if (legacyPart != null) {
			if (legacyPart instanceof IWorkbenchPart3) {
				return ((IWorkbenchPart3) legacyPart).getPartProperty(key);
			}
		} else {
			return (String)propertyCache.get(key);
		}
		return null;
	}
    
    @Override
	public void addPartPropertyListener(IPropertyChangeListener listener) {
    	if (isDisposed()) {
    		return;
    	}
    	partChangeListeners.add(listener);
    }
    
    @Override
	public void removePartPropertyListener(IPropertyChangeListener listener) {
    	if (isDisposed()) {
    		return;
    	}
    	partChangeListeners.remove(listener);
    }
    
    protected void firePartPropertyChange(PropertyChangeEvent event) {
		Object[] l = partChangeListeners.getListeners();
		for (int i = 0; i < l.length; i++) {
			((IPropertyChangeListener) l[i]).propertyChange(event);
		}
	}
    
    protected void createPartProperties(IWorkbenchPart3 workbenchPart) {
		Iterator i = propertyCache.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry e = (Map.Entry) i.next();
			workbenchPart.setPartProperty((String) e.getKey(), (String) e.getValue());
		}
	}
    
    @Override
	public int computePreferredSize(boolean width, int availableParallel,
            int availablePerpendicular, int preferredResult) {

		ISizeProvider sizeProvider = (ISizeProvider) Util.getAdapter(legacyPart,
				ISizeProvider.class);
        if (sizeProvider != null) {
            return sizeProvider.computePreferredSize(width, availableParallel, availablePerpendicular, preferredResult);
        }

        return preferredResult;
    }

    @Override
	public int getSizeFlags(boolean width) {
		ISizeProvider sizeProvider = (ISizeProvider) Util.getAdapter(legacyPart,
				ISizeProvider.class);
        if (sizeProvider != null) {
            return sizeProvider.getSizeFlags(width);
        }
        return 0;
    }
    
	@Override
	public IWorkbenchPage getPage() {
		return page;
	}

	public void setPage(IWorkbenchPage newPage) {
		page = newPage;
	}

	@Override
	public String getPartName() {
		return part.getLocalizedLabel();
	}

	@Override
	public String getContentDescription() {
		IWorkbenchPart workbenchPart = getPart(false);
		if (workbenchPart instanceof IWorkbenchPart2) {
			return ((IWorkbenchPart2) workbenchPart).getContentDescription();
		}
		return workbenchPart.getTitle();
	}

	@Override
	public boolean isDirty() {
		IWorkbenchPart part = getPart(false);
		if (part instanceof ISaveablePart) {
			return ((ISaveablePart) part).isDirty();
		}
		return false;
	}

	public void invalidate() {
		doDisposePart();
	}

	public final PartPane getPane() {
		return new PartPane() {
			@Override
			public Control getControl() {
				return part == null ? null : (Control) part.getWidget();
			}
		};
	}
}
