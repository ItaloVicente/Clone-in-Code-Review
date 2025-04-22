package org.eclipse.ui.navigator;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.internal.navigator.wizards.CommonWizardDescriptor;
import org.eclipse.ui.internal.navigator.wizards.CommonWizardDescriptorManager;
import org.eclipse.ui.internal.navigator.wizards.WizardShortcutAction;
import org.eclipse.ui.wizards.IWizardDescriptor;
import org.eclipse.ui.wizards.IWizardRegistry;

public final class WizardActionGroup extends ActionGroup {

	public static final String TYPE_NEW = "new"; //$NON-NLS-1$

	public static final String TYPE_IMPORT = "import"; //$NON-NLS-1$

	public static final String TYPE_EXPORT = "export"; //$NON-NLS-1$

	private static final CommonWizardDescriptor[] NO_DESCRIPTORS = new CommonWizardDescriptor[0];
	
	private static final String[] NO_IDS = new String[0];  
	
	private CommonWizardDescriptor[] descriptors;

	private Map<String, IAction> actions;

	private IWorkbenchWindow window;

	private IWizardRegistry wizardRegistry; 

	private boolean disposed = false;

	private String type;

	private INavigatorContentService contentService;

	public WizardActionGroup(IWorkbenchWindow aWindow,
			IWizardRegistry aWizardRegistry, String aType) {
		super();
		Assert.isNotNull(aWindow);
		Assert.isNotNull(aWizardRegistry);
		Assert
				.isTrue(aType != null
						&& (TYPE_NEW.equals(aType) || TYPE_IMPORT.equals(aType) || TYPE_EXPORT
								.equals(aType)));
		window = aWindow;
		wizardRegistry = aWizardRegistry;
		type = aType;

	}
	

	public WizardActionGroup(IWorkbenchWindow aWindow,
			IWizardRegistry aWizardRegistry, String aType, INavigatorContentService aContentService) {
		this(aWindow, aWizardRegistry, aType);
		contentService = aContentService;

	}

	@Override
	public void setContext(ActionContext aContext) {
		Assert.isTrue(!disposed);

		super.setContext(aContext);
		if (aContext != null) {
			ISelection selection = aContext.getSelection();
			Object element = null;
			if (selection instanceof IStructuredSelection) {
				element = ((IStructuredSelection) selection).getFirstElement();
			}
			if(element == null) {
				element = Collections.EMPTY_LIST;
			}
			setWizardActionDescriptors(CommonWizardDescriptorManager.getInstance()
					.getEnabledCommonWizardDescriptors(element, type, contentService));
		} else {
			setWizardActionDescriptors(NO_DESCRIPTORS);
		}
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		Assert.isTrue(!disposed);
 
		if (descriptors != null) { 
			Map<String, SortedSet> groups = findGroups(); 
			SortedSet sortedWizards = null;
			String menuGroupId = null;
			for (Iterator<String> menuGroupItr = groups.keySet().iterator(); menuGroupItr.hasNext();) {
				menuGroupId = menuGroupItr.next();
				sortedWizards = groups.get(menuGroupId); 
				menu.add(new Separator(menuGroupId));
				for (Iterator wizardItr = sortedWizards.iterator(); wizardItr.hasNext();) {
					menu.add((IAction) wizardItr.next());				
				}
			} 
		} 
	}

	private synchronized Map/*<String, SortedSet<IAction>>*/<String, SortedSet>  findGroups() {  
		IAction action = null;
		Map<String, SortedSet> groups = new TreeMap<String, SortedSet>();
		SortedSet<IAction> sortedWizards = null;
		String menuGroupId = null;
		for (int i = 0; i < descriptors.length; i++) {
			menuGroupId = descriptors[i].getMenuGroupId() != null ? 
							descriptors[i].getMenuGroupId() : CommonWizardDescriptor.DEFAULT_MENU_GROUP_ID;
			sortedWizards = groups.get(menuGroupId);
			if(sortedWizards == null) {
				groups.put(descriptors[i].getMenuGroupId(), sortedWizards = new TreeSet<IAction>(ActionComparator.INSTANCE));
			}  
			if ((action = getAction(descriptors[i].getWizardId())) != null) {
				sortedWizards.add(action); 
			}			
		}
		return groups;
	}


	@Override
	public void dispose() {
		super.dispose();
		actions = null;
		window = null;
		descriptors = null;
		wizardRegistry = null;
		disposed = true;
	}

	protected IAction getAction(String id) {
		if (id == null || id.length() == 0) {
			return null;
		}

		IAction action = getActions().get(id);
		if (action == null) {
			IWizardDescriptor descriptor = wizardRegistry.findWizard(id);
			if (descriptor != null) {
				action = new WizardShortcutAction(window, descriptor);
				getActions().put(id, action);
			}
		}

		return action;
	}

	protected Map<String, IAction> getActions() {
		if (actions == null) {
			actions = new HashMap<String, IAction>();
		}
		return actions;
	}

	public synchronized String[] getWizardActionIds() { 
		if(descriptors != null && descriptors.length > 0) { 
			String[] wizardActionIds = new String[descriptors.length]; 
			for (int i = 0; i < descriptors.length; i++) {
				wizardActionIds[i] = descriptors[i].getWizardId();
			}
			return wizardActionIds;
		}
		return NO_IDS;
	}

	private synchronized void setWizardActionDescriptors(CommonWizardDescriptor[] theWizardDescriptors) { 
		descriptors = theWizardDescriptors;
	}
	  
	private static class ActionComparator implements Comparator {
		
		private static final ActionComparator INSTANCE = new ActionComparator();
		@Override
		public int compare(Object arg0, Object arg1) {
			return ((IAction)arg0).getText().compareTo(((IAction)arg1).getText());
		}
	} 
}
