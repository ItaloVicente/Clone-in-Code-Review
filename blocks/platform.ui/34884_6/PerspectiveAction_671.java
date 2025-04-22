
package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.internal.e4.compatibility.ModeledPageLayout;
import org.eclipse.ui.internal.registry.ActionSetRegistry;
import org.eclipse.ui.internal.registry.IActionSetDescriptor;
import org.eclipse.ui.internal.registry.PerspectiveDescriptor;

public class Perspective {
    protected PerspectiveDescriptor descriptor;

    protected WorkbenchPage page;

    protected boolean editorHidden = false;
    protected boolean editorAreaRestoreOnUnzoom = false;
    protected int editorAreaState = IWorkbenchPage.STATE_RESTORED;

    
    protected ArrayList alwaysOnActionSets;

    protected ArrayList alwaysOffActionSets;
    
    protected Collection hideMenuIDs;
    
    protected Collection hideToolBarIDs;


    private Map mapIDtoViewLayoutRec;

    protected boolean fixed;

    protected ArrayList showInPartIds;

    protected HashMap showInTimes = new HashMap();

    protected IMemento memento;

    private IWorkbenchPartReference oldPartRef = null;

    protected boolean shouldHideEditorsOnActivate = false;

	protected MPerspective layout;

	public Perspective(PerspectiveDescriptor desc, MPerspective layout, WorkbenchPage page) {
        this(page);
		this.layout = layout;
        descriptor = desc;
    }

	public void initActionSets() {
		if (descriptor != null) {
			List temp = new ArrayList();
			createInitialActionSets(temp,
					ModeledPageLayout.getIds(layout, ModeledPageLayout.ACTION_SET_TAG));
			for (Iterator iter = temp.iterator(); iter.hasNext();) {
				IActionSetDescriptor descriptor = (IActionSetDescriptor) iter.next();
				if (!alwaysOnActionSets.contains(descriptor)) {
					alwaysOnActionSets.add(descriptor);
				}
			}
		}

	}
	protected Perspective(WorkbenchPage page) {
        this.page = page;
        alwaysOnActionSets = new ArrayList(2);
        alwaysOffActionSets = new ArrayList(2);
        hideMenuIDs = new HashSet();
        hideToolBarIDs = new HashSet();
        
        
        mapIDtoViewLayoutRec = new HashMap();
    }


    protected void createInitialActionSets(List outputList, List stringList) {
        ActionSetRegistry reg = WorkbenchPlugin.getDefault()
                .getActionSetRegistry();
        Iterator iter = stringList.iterator();
        while (iter.hasNext()) {
            String id = (String) iter.next();
            IActionSetDescriptor desc = reg.findActionSet(id);
            if (desc != null) {
				outputList.add(desc);
			} else {
			}
        }
    }

    public void dispose() {
		mapIDtoViewLayoutRec.clear();
    }

    public IPerspectiveDescriptor getDesc() {
        return descriptor;
    }


    public String[] getNewWizardShortcuts() {
		return page.getNewWizardShortcuts();
    }

    public String[] getPerspectiveShortcuts() {
		return page.getPerspectiveShortcuts();
    }

    public ArrayList getShowInPartIds() {
		return page.getShowInPartIds();
    }

    public long getShowInTime(String partId) {
        Long t = (Long) showInTimes.get(partId);
        return t == null ? 0L : t.longValue();
    }

    public String[] getShowViewShortcuts() {
		return page.getShowViewShortcuts();
    }

    private void removeAlwaysOn(IActionSetDescriptor descriptor) {
        if (descriptor == null) {
            return;
        }
        if (!alwaysOnActionSets.contains(descriptor)) {
            return;
        }
        
        alwaysOnActionSets.remove(descriptor);
        if (page != null) {
            page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_HIDE);
        }
    }
    
    protected void addAlwaysOff(IActionSetDescriptor descriptor) {
        if (descriptor == null) {
            return;
        }
        if (alwaysOffActionSets.contains(descriptor)) {
            return;
        }
        alwaysOffActionSets.add(descriptor);
        if (page != null) {
            page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_MASK);
        }
        removeAlwaysOn(descriptor);
    }
    
    protected void addAlwaysOn(IActionSetDescriptor descriptor) {
        if (descriptor == null) {
            return;
        }
        if (alwaysOnActionSets.contains(descriptor)) {
            return;
        }
        alwaysOnActionSets.add(descriptor);
        if (page != null) {
            page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_SHOW);
        }
        removeAlwaysOff(descriptor);
    }
    
    private void removeAlwaysOff(IActionSetDescriptor descriptor) {
        if (descriptor == null) {
            return;
        }
        if (!alwaysOffActionSets.contains(descriptor)) {
            return;
        }
        alwaysOffActionSets.remove(descriptor);
        if (page != null) {
            page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_UNMASK);
        }
    }
    
    protected ArrayList getPerspectiveExtensionActionSets() {
		return page.getPerspectiveExtensionActionSets(descriptor.getOriginalId());
    }
    
    public void turnOnActionSets(IActionSetDescriptor[] newArray) {
        for (int i = 0; i < newArray.length; i++) {
            IActionSetDescriptor descriptor = newArray[i];
            
			addActionSet(descriptor);
        }
    }
    
    public void turnOffActionSets(IActionSetDescriptor[] toDisable) {
        for (int i = 0; i < toDisable.length; i++) {
            IActionSetDescriptor descriptor = toDisable[i];
            
            turnOffActionSet(descriptor);
        }
    }

    public void turnOffActionSet(IActionSetDescriptor toDisable) {
		removeActionSet(toDisable);
    }
    


    public IWorkbenchPartReference getOldPartRef() {
        return oldPartRef;
    }

    public void setOldPartRef(IWorkbenchPartReference oldPartRef) {
        this.oldPartRef = oldPartRef;
    }

    protected void addActionSet(IActionSetDescriptor newDesc) {
    	IContextService service = page.getWorkbenchWindow().getService(IContextService.class);
    	try {
			service.deferUpdates(true);
			for (int i = 0; i < alwaysOnActionSets.size(); i++) {
				IActionSetDescriptor desc = (IActionSetDescriptor) alwaysOnActionSets
						.get(i);
				if (desc.getId().equals(newDesc.getId())) {
					removeAlwaysOn(desc);
					removeAlwaysOff(desc);
					break;
				}
			}
			addAlwaysOn(newDesc);
			final String actionSetID = newDesc.getId();

			String tag = ModeledPageLayout.ACTION_SET_TAG + actionSetID;
			if (!layout.getTags().contains(tag)) {
				layout.getTags().add(tag);
			}
		} finally {
    		service.deferUpdates(false);
    	}
    }

	protected void removeActionSet(IActionSetDescriptor toRemove) {
		String id = toRemove.getId();
    	IContextService service = page.getWorkbenchWindow().getService(IContextService.class);
    	try {
			service.deferUpdates(true);
			for (int i = 0; i < alwaysOnActionSets.size(); i++) {
				IActionSetDescriptor desc = (IActionSetDescriptor) alwaysOnActionSets
						.get(i);
				if (desc.getId().equals(id)) {
					removeAlwaysOn(desc);
					break;
				}
			}

			for (int i = 0; i < alwaysOffActionSets.size(); i++) {
				IActionSetDescriptor desc = (IActionSetDescriptor) alwaysOffActionSets
						.get(i);
				if (desc.getId().equals(id)) {
					removeAlwaysOff(desc);
					break;
				}
			}
			addAlwaysOff(toRemove);
			String tag = ModeledPageLayout.ACTION_SET_TAG + id;
			if (layout.getTags().contains(tag)) {
				layout.getTags().remove(tag);
			}
		} finally {
    		service.deferUpdates(false);
    	}
    }
    
    public IActionSetDescriptor[] getAlwaysOnActionSets() {
        return (IActionSetDescriptor[]) alwaysOnActionSets.toArray(new IActionSetDescriptor[alwaysOnActionSets.size()]);
    }
    
    public IActionSetDescriptor[] getAlwaysOffActionSets() {
        return (IActionSetDescriptor[]) alwaysOffActionSets.toArray(new IActionSetDescriptor[alwaysOffActionSets.size()]);
    }
	
	public Collection getHiddenMenuItems() {
		return hideMenuIDs;
	}
	
	public Collection getHiddenToolbarItems() {
		return hideToolBarIDs;
	}
	
	public void updateActionBars() {
		page.getActionBars().getMenuManager().updateAll(true);
		page.resetToolBarLayout();
	}

}
