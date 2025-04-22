package org.eclipse.ui.views.properties;

import java.util.HashSet;

import org.eclipse.osgi.util.NLS;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.RegistryFactory;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.internal.views.ViewsPlugin;
import org.eclipse.ui.internal.views.properties.PropertiesMessages;
import org.eclipse.ui.part.IContributedContentsView;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.IShowInSource;
import org.eclipse.ui.part.IShowInTarget;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PageBookView;
import org.eclipse.ui.part.ShowInContext;

public class PropertySheet extends PageBookView implements ISelectionListener, IShowInTarget, IShowInSource, IRegistryEventListener {
    public static final String HELP_CONTEXT_PROPERTY_SHEET_VIEW = IPropertiesHelpContextIds.PROPERTY_SHEET_VIEW;

    private static final String EXT_POINT = "org.eclipse.ui.propertiesView"; //$NON-NLS-1$
    
    private ISelection bootstrapSelection;

    private ISelection currentSelection;

	private IWorkbenchPart currentPart;

	private IAction pinPropertySheetAction;

	private HashSet ignoredViews;
	
    public PropertySheet() {
        super();
        pinPropertySheetAction = new PinPropertySheetAction();
        RegistryFactory.getRegistry().addListener(this, EXT_POINT);
    }

    @Override
	protected IPage createDefaultPage(PageBook book) {
        IPageBookViewPage page = (IPageBookViewPage) ViewsPlugin.getAdapter(this,
                IPropertySheetPage.class, false);
        if(page == null) {
        	page = new PropertySheetPage();
        }
        initPage(page);
        page.createControl(book);
        return page;
    }

    @Override
	public void createPartControl(Composite parent) {
        super.createPartControl(parent);
         
        pinPropertySheetAction.addPropertyChangeListener(new IPropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (IAction.CHECKED.equals(event.getProperty())) {
					updateContentDescription();
				}
			}
		});
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
		menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menuManager.add(pinPropertySheetAction);

		IToolBarManager toolBarManager = getViewSite().getActionBars()
				.getToolBarManager();
		menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		toolBarManager.add(pinPropertySheetAction);
		
        getSite().getPage().getWorkbenchWindow().getWorkbench().getHelpSystem()
				.setHelp(getPageBook(),
						IPropertiesHelpContextIds.PROPERTY_SHEET_VIEW);
    }

    @Override
	public void dispose() {
        super.dispose();

        getSite().getPage().removePostSelectionListener(this);
        RegistryFactory.getRegistry().removeListener(this);
        
        currentPart = null;
        currentSelection = null;
        pinPropertySheetAction = null;
    }

    @Override
	protected PageRec doCreatePage(IWorkbenchPart part) {
    	if(part instanceof PropertySheet) {
    		return null;
    	}
        IPropertySheetPage page = (IPropertySheetPage) ViewsPlugin.getAdapter(part,
                IPropertySheetPage.class, false);
        if (page != null) {
            if (page instanceof IPageBookViewPage) {
				initPage((IPageBookViewPage) page);
			}
            page.createControl(getPageBook());
            return new PageRec(part, page);
        }

        return null;
    }

    @Override
	protected void doDestroyPage(IWorkbenchPart part, PageRec rec) {
        IPropertySheetPage page = (IPropertySheetPage) rec.page;
        page.dispose();
        rec.dispose();
    }

    @Override
	protected IWorkbenchPart getBootstrapPart() {
        IWorkbenchPage page = getSite().getPage();
        if (page != null) {
            bootstrapSelection = page.getSelection();
            return page.getActivePart();
        }
        return null;
    }

    @Override
	public void init(IViewSite site) throws PartInitException {
   		site.getPage().addPostSelectionListener(this);
   		super.init(site);
    }

	@Override
	public void saveState(IMemento memento) {
		String secondaryId = getViewSite().getSecondaryId();
		if (null == secondaryId) {
			super.saveState(memento);
		} else {
			getViewSite().getPage().hideView(this);
		}
	}

    @Override
	protected boolean isImportant(IWorkbenchPart part) {
    	String partID = part.getSite().getId();
		boolean isPropertyView = getSite().getId().equals(partID);
		return !isPinned() && !isPropertyView && !isViewIgnored(partID);
    }

	@Override
	public void partClosed(IWorkbenchPart part) {
		if (part.equals(currentPart)) {
			if (isPinned())
				pinPropertySheetAction.setChecked(false);
			currentPart = null;
		}
		super.partClosed(part);
	}
    
	@Override
	protected void partVisible(IWorkbenchPart part) {
	    super.partVisible(part);
	}
	
    @Override
	protected void partHidden(IWorkbenchPart part) {
    }
    
    @Override
	public void partActivated(IWorkbenchPart part) {
        IContributedContentsView view = (IContributedContentsView) ViewsPlugin.getAdapter(part,
                IContributedContentsView.class, true);
        IWorkbenchPart source = null;
        if (view != null) {
			source = view.getContributingPart();
		}
        if (source != null) {
			super.partActivated(source);
		} else {
			super.partActivated(part);
		}

        if(isImportant(part)) {
        	currentPart = part;
        	currentSelection = null;
        }
        
        if (bootstrapSelection != null) {
            IPropertySheetPage page = (IPropertySheetPage) getCurrentPage();
            if (page != null) {
				page.selectionChanged(part, bootstrapSelection);
			}
            bootstrapSelection = null;
        }
    }

    @Override
	public void selectionChanged(IWorkbenchPart part, ISelection sel) {
		if (sel == null || !isImportant(part) || sel.equals(currentSelection)) {
			return;
		}
		
		if(part == null || !part.equals(currentPart)){
		    return;
		}
        
        currentPart = part;
        currentSelection = sel;
        
        IPropertySheetPage page = (IPropertySheetPage) getCurrentPage();
        if (page != null) {
			page.selectionChanged(currentPart, currentSelection);
		}
        
        updateContentDescription();
    }

	private void updateContentDescription() {
		if (isPinned() && currentPart != null) {
			setContentDescription(NLS.bind(PropertiesMessages.Selection_description, currentPart.getTitle()));
		} else {
			setContentDescription(""); //$NON-NLS-1$
		}
	}
    
	@Override
	protected Object getViewAdapter(Class key) {
		if (ISaveablePart.class.equals(key)) {
			return getSaveablePart();
		}
		return super.getViewAdapter(key);
	}

	protected ISaveablePart getSaveablePart() {
		IWorkbenchPart part = getCurrentContributingPart();
		if (part instanceof ISaveablePart) {
			return (ISaveablePart) part;
		}
		return null;
	}
	
	public boolean isPinned() {
		return pinPropertySheetAction != null && pinPropertySheetAction.isChecked();
	}

	@Override
	public ShowInContext getShowInContext() {
		return new PropertyShowInContext(currentPart, currentSelection);
	}
	
	@Override
	public boolean show(ShowInContext aContext) {
		if (!isPinned()
				&& aContext instanceof PropertyShowInContext) {
			PropertyShowInContext context = (PropertyShowInContext) aContext;
			partActivated(context.getPart());
			selectionChanged(context.getPart(), context.getSelection());
			return true;
		}
		return false;
	}

	public void setPinned(boolean pinned) {
		pinPropertySheetAction.setChecked(pinned);
		updateContentDescription();
	}
	
	private HashSet getIgnoredViews() {
		if (ignoredViews == null) {
			ignoredViews = new HashSet();
	        IExtensionRegistry registry = RegistryFactory.getRegistry();
	        IExtensionPoint ep = registry.getExtensionPoint(EXT_POINT);
			if (ep != null) {
				IExtension[] extensions = ep.getExtensions();
				for (int i = 0; i < extensions.length; i++) {
					IConfigurationElement[] elements = extensions[i].getConfigurationElements();
					for (int j = 0; j < elements.length; j++) {
						if ("excludeSources".equalsIgnoreCase(elements[j].getName())) { //$NON-NLS-1$
							String id = elements[j].getAttribute("id"); //$NON-NLS-1$
							if (id != null)
								ignoredViews.add(id);
						}
					}
				}
			}
		}
		return ignoredViews;
	}

	private boolean isViewIgnored(String partID) {
		return getIgnoredViews().contains(partID);
	}
	
	@Override
	public void added(IExtension[] extensions) {
		ignoredViews = null;
	}

	@Override
	public void added(IExtensionPoint[] extensionPoints) {
		ignoredViews = null;
	}

	@Override
	public void removed(IExtension[] extensions) {
		ignoredViews = null;
	}

	@Override
	public void removed(IExtensionPoint[] extensionPoints) {
		ignoredViews = null;
	}
	
}
