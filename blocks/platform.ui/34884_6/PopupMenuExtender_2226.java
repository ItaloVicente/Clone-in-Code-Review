package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.jface.action.AbstractGroupMarker;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.internal.provisional.action.IToolBarContributionItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.registry.ActionSetRegistry;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.services.IDisposable;

public class PluginActionSetBuilder extends PluginActionBuilder {

    private PluginActionSet actionSet;

    private IWorkbenchWindow window;

    private ArrayList adjunctContributions = new ArrayList(0);
    
	public static class Binding implements IDisposable {
        PluginActionSetBuilder builder;
        PluginActionSet set;
        IWorkbenchWindow window;
		IExtensionTracker tracker;

		@Override
		public void dispose() {
			if (tracker != null) {
				tracker.unregisterObject(set.getConfigElement()
						.getDeclaringExtension(), this);
				tracker = null;
			}
		}
    }

    public PluginActionSetBuilder() {
    }

    public void buildMenuAndToolBarStructure(PluginActionSet set,
            IWorkbenchWindow window) {
        this.actionSet = set;
        this.window = window;
        cache = null;
        currentContribution = null;
        targetID = null;
        targetContributionTag = IWorkbenchRegistryConstants.TAG_ACTION_SET;

        readElements(new IConfigurationElement[] { set.getConfigElement() });

        if (cache != null) {
            for (int i = 0; i < cache.size(); i++) {
                ActionSetContribution contribution = (ActionSetContribution) cache
                        .get(i);
                contribution.contribute(actionSet.getBars(), true, true);
                if (contribution.isAdjunctContributor()) {
                    adjunctContributions.add(contribution);
                }
            }
        }
        for (int i = 0; i < adjunctContributions.size(); i++) {
            ActionSetContribution contribution = (ActionSetContribution) adjunctContributions
                    .get(i);
            ActionSetActionBars bars = actionSet.getBars();
            for (int j = 0; j < contribution.adjunctActions.size(); j++) {
                ActionDescriptor adjunctAction = (ActionDescriptor) contribution.adjunctActions
                        .get(j);
                contribution
                        .contributeAdjunctCoolbarAction(adjunctAction, bars);
            }
        }
        
        registerBinding(set);
    }

    @Override
	protected ActionDescriptor createActionDescriptor(
            IConfigurationElement element) {
        boolean pullDownStyle = false;
        String style = element.getAttribute(IWorkbenchRegistryConstants.ATT_STYLE);
        if (style != null) {
            pullDownStyle = style.equals(ActionDescriptor.STYLE_PULLDOWN);
        } else {
            String pulldown = element.getAttribute(ActionDescriptor.STYLE_PULLDOWN);
            pullDownStyle = pulldown != null && pulldown.equals("true"); //$NON-NLS-1$
        }

        ActionDescriptor desc = null;
        if (pullDownStyle) {
			desc = new ActionDescriptor(element,
                    ActionDescriptor.T_WORKBENCH_PULLDOWN, window);
		} else {
			desc = new ActionDescriptor(element, ActionDescriptor.T_WORKBENCH,
                    window);
		}
        WWinPluginAction action = (WWinPluginAction) desc.getAction();
        action.setActionSetId(actionSet.getDesc().getId());
        actionSet.addPluginAction(action);
        return desc;
    }

    @Override
	protected BasicContribution createContribution() {
        return new ActionSetContribution(actionSet.getDesc().getId(), window);
    }

    public static IContributionItem findInsertionPoint(String startId,
            String sortId, IContributionManager mgr, boolean startVsEnd) {
        IContributionItem[] items = mgr.getItems();

        int insertIndex = 0;
        while (insertIndex < items.length) {
            if (startId.equals(items[insertIndex].getId())) {
				break;
			}
            ++insertIndex;
        }
        if (insertIndex >= items.length) {
			return null;
		}

        int compareMetric = 0;
        if (startVsEnd) {
			compareMetric = 1;
		}

        for (int nX = insertIndex + 1; nX < items.length; nX++) {
            IContributionItem item = items[nX];
            if (item.isSeparator() || item.isGroupMarker()) {
                break;
            }
            if (item instanceof IActionSetContributionItem) {
                if (sortId != null) {
                    String testId = ((IActionSetContributionItem) item)
                            .getActionSetId();
                    if (sortId.compareTo(testId) < compareMetric) {
						break;
					}
                }
                insertIndex = nX;
            } else {
                break;
            }
        }
        return items[insertIndex];
    }

            WorkbenchWindow window) {
        PluginActionSetBuilder[] builders = new PluginActionSetBuilder[pluginActionSets
                .size()];
        for (int i = 0; i < pluginActionSets.size(); i++) {
            PluginActionSet set = (PluginActionSet) pluginActionSets.get(i);
            PluginActionSetBuilder builder = new PluginActionSetBuilder();
            builder.readActionExtensions(set, window);
            builders[i] = builder;
        }
        for (int i = 0; i < builders.length; i++) {
            PluginActionSetBuilder builder = builders[i];
            builder.processAdjunctContributions();
        }
    }

    protected void processAdjunctContributions() {
        for (int i = 0; i < adjunctContributions.size(); i++) {
            ActionSetContribution contribution = (ActionSetContribution) adjunctContributions
                    .get(i);
            ActionSetActionBars bars = actionSet.getBars();
            for (int j = 0; j < contribution.adjunctActions.size(); j++) {
                ActionDescriptor adjunctAction = (ActionDescriptor) contribution.adjunctActions
                        .get(j);
                contribution
                        .contributeAdjunctCoolbarAction(adjunctAction, bars);
            }
        }
    }

    protected void readActionExtensions(PluginActionSet set,
            IWorkbenchWindow window) {
        this.actionSet = set;
        this.window = window;
        cache = null;
        currentContribution = null;
        targetID = null;
        targetContributionTag = IWorkbenchRegistryConstants.TAG_ACTION_SET;

        readElements(new IConfigurationElement[] { set.getConfigElement() });

        if (cache != null) {
            for (int i = 0; i < cache.size(); i++) {
                ActionSetContribution contribution = (ActionSetContribution) cache
                        .get(i);
                contribution.contribute(actionSet.getBars(), true, true);
                if (contribution.isAdjunctContributor()) {
                    adjunctContributions.add(contribution);
                }
            }
            
            registerBinding(set);
            
        } else {
            WorkbenchPlugin
                    .log("Action Set is empty: " + set.getDesc().getId()); //$NON-NLS-1$
        }
    }
    
    private void registerBinding(final PluginActionSet set) {
    	final IExtensionTracker tracker = window.getExtensionTracker();
    	 
    	final Binding binding = new Binding();
        binding.builder = this;
        binding.set = set;
        binding.window = window;
		binding.tracker = tracker;
        tracker.registerObject(
                set.getConfigElement().getDeclaringExtension(), binding,
                IExtensionTracker.REF_STRONG);
		set.setBuilder(binding);
    }

    private static class ActionSetContribution extends BasicContribution {
        private String actionSetId;

        private WorkbenchWindow window;

        protected ArrayList adjunctActions = new ArrayList(0);

        public ActionSetContribution(String id, IWorkbenchWindow window) {
            super();
            actionSetId = id;
            this.window = (WorkbenchWindow) window;
        }

        @Override
		protected void addGroup(IContributionManager mgr, String name) {
            IContributionItem refItem = findInsertionPoint(
                    IWorkbenchActionConstants.MB_ADDITIONS, actionSetId, mgr,
                    true);
            ActionSetSeparator group = new ActionSetSeparator(name, actionSetId);
            if (refItem == null) {
                mgr.add(group);
            } else {
                mgr.insertAfter(refItem.getId(), group);
            }
        }

        public void contribute(IActionBars bars, boolean menuAppendIfMissing,
                boolean toolAppendIfMissing) {

            IMenuManager menuMgr = bars.getMenuManager();
            IToolBarManager toolBarMgr = bars.getToolBarManager();
            if (menus != null && menuMgr != null) {
                for (int i = 0; i < menus.size(); i++) {
                    IConfigurationElement menuElement = (IConfigurationElement) menus
                            .get(i);
                    contributeMenu(menuElement, menuMgr, menuAppendIfMissing);
                }
            }

            if (actions != null) {
                for (int i = 0; i < actions.size(); i++) {
                    ActionDescriptor ad = (ActionDescriptor) actions.get(i);
                    if (menuMgr != null) {
						contributeMenuAction(ad, menuMgr, menuAppendIfMissing);
					}
                    if (toolBarMgr != null) {
                        if (bars instanceof ActionSetActionBars) {
                            contributeCoolbarAction(ad,
                                    (ActionSetActionBars) bars);
                        } else {
                            contributeToolbarAction(ad, toolBarMgr,
                                    toolAppendIfMissing);
                        }
                    }
                }
            }
        }

        protected void contributeAdjunctCoolbarAction(ActionDescriptor ad,
                ActionSetActionBars bars) {
            String toolBarId = ad.getToolbarId();
            String toolGroupId = ad.getToolbarGroupId();

            String contributingId = bars.getActionSetId();
            ICoolBarManager coolBarMgr = bars.getCoolBarManager();
            if (coolBarMgr == null) {
                return;
            }

            PluginAction action = ad.getAction();
            ActionContributionItem actionContribution = new PluginActionCoolBarContributionItem(
                    action);
            actionContribution.setMode(ad.getMode());

            bars.addAdjunctContribution(actionContribution);

            IToolBarManager toolBarManager = bars.getToolBarManager(toolBarId);

            IContributionItem groupMarker = toolBarManager.find(toolGroupId);
            if (groupMarker == null) {
                toolBarManager.add(new Separator(toolGroupId));
            }
            IContributionItem refItem = findAlphabeticalOrder(toolGroupId,
                    contributingId, toolBarManager);
            if (refItem != null && refItem.getId() != null) {
                toolBarManager.insertAfter(refItem.getId(), actionContribution);
            } else {
                toolBarManager.add(actionContribution);
            }
            toolBarManager.update(false);

        }

        protected void contributeCoolbarAction(ActionDescriptor ad,
                ActionSetActionBars bars) {
            String toolBarId = ad.getToolbarId();
            String toolGroupId = ad.getToolbarGroupId();
            if (toolBarId == null && toolGroupId == null) {
				return;
			}

            String contributingId = bars.getActionSetId();

            if (toolBarId == null || toolBarId.equals("")) { //$NON-NLS-1$ 
                toolBarId = contributingId;
            }

            if (!toolBarId.equals(contributingId)) {
                if (!isValidCoolItemId(toolBarId, window)) {
                    toolBarId = contributingId;
                } else {
                    adjunctActions.add(ad);
                    return;
                }
            }

            PluginAction action = ad.getAction();
            ActionContributionItem actionContribution = new PluginActionCoolBarContributionItem(
                    action);
            actionContribution.setMode(ad.getMode());

            IToolBarManager toolBar = bars.getToolBarManager(toolBarId);

            IContributionItem groupMarker = toolBar.find(toolGroupId);
            if (groupMarker == null) {
                toolBar.add(new Separator(toolGroupId));
            }
            toolBar.prependToGroup(toolGroupId, actionContribution);
            toolBar.update(false);

        }

        private boolean isValidCoolItemId(String id, WorkbenchWindow window) {
            ActionSetRegistry registry = WorkbenchPlugin.getDefault()
                    .getActionSetRegistry();
            if (registry.findActionSet(id) != null) {
				return true;
			}
            if (window != null) {
                return window.isWorkbenchCoolItemId(id);
            }
            return false;
        }

        @Override
		protected void insertMenuGroup(IMenuManager menu,
                AbstractGroupMarker marker) {
            if (actionSetId != null) {
                IContributionItem[] items = menu.getItems();
                for (int i = 0; i < items.length; i++) {
                    IContributionItem item = items[i];
                    if (item.isSeparator() || item.isGroupMarker()) {
                        if (item instanceof IActionSetContributionItem) {
                            String testId = ((IActionSetContributionItem) item)
                                    .getActionSetId();
                            if (actionSetId.compareTo(testId) < 0) {
                                menu.insertBefore(items[i].getId(), marker);
                                return;
                            }
                        }
                    }
                }
            }

            menu.add(marker);
        }

        private IContributionItem findAlphabeticalOrder(String startId,
                String itemId, IContributionManager mgr) {
            IContributionItem[] items = mgr.getItems();
            int insertIndex = 0;

            while (insertIndex < items.length) {
                IContributionItem item = items[insertIndex];
                if (startId != null && startId.equals(item.getId())) {
					break;
				}
                ++insertIndex;
            }

            for (int i = insertIndex + 1; i < items.length; i++) {
                IContributionItem item = items[i];
                if (item.isGroupMarker()) {
					break;
				}

                String testId = null;
                if (item instanceof PluginActionCoolBarContributionItem) {
                    testId = ((PluginActionCoolBarContributionItem) item)
                            .getActionSetId();
                }
                if (testId == null) {
                    break;
                }

                if (itemId != null && testId != null) {
                    if (itemId.compareTo(testId) < 1) {
						break;
					}
                }
                insertIndex = i;
            }
            if (insertIndex >= items.length) {
                return null;
            }
            return items[insertIndex];
        }

        public boolean isAdjunctContributor() {
            return adjunctActions.size() > 0;
        }

        @Override
		protected void insertAfter(IContributionManager mgr, String refId,
                IContributionItem item) {
            IContributionItem refItem = findInsertionPoint(refId, actionSetId,
                    mgr, true);
            if (refItem != null) {
                mgr.insertAfter(refItem.getId(), item);
            } else {
                WorkbenchPlugin
                        .log("Reference item " + refId + " not found for action " + item.getId()); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }

        protected void revokeContribution(WorkbenchWindow window,
                IActionBars bars, String id) {
            revokeActionSetFromMenu(window.getMenuManager(), id);

            revokeActionSetFromCoolbar(window.getCoolBarManager2(), id);
        }

        protected void revokeAdjunctCoolbarAction(ActionDescriptor ad,
                ActionSetActionBars bars) {
            String toolBarId = ad.getToolbarId();
            ICoolBarManager coolBarMgr = bars.getCoolBarManager();
            PluginAction action = ad.getAction();
            PluginActionCoolBarContributionItem actionContribution = new PluginActionCoolBarContributionItem(
                    action);
            actionContribution.setMode(ad.getMode());

            bars.removeAdjunctContribution(actionContribution);

            IContributionItem cbItem = coolBarMgr.find(toolBarId);
            if (cbItem != null) {
				coolBarMgr.remove(cbItem);
			}

        }

        private void revokeActionSetFromMenu(IMenuManager menuMgr,
                String actionsetId) {
            IContributionItem[] items = menuMgr.getItems();
            ArrayList itemsToRemove = new ArrayList();
            String id;
            for (int i = 0; i < items.length; i++) {
				if (items[i] instanceof IMenuManager) {
                    revokeActionSetFromMenu((IMenuManager) items[i],
                            actionsetId);
                } else if (items[i] instanceof ActionSetContributionItem) {
                    id = ((ActionSetContributionItem) items[i])
                            .getActionSetId();
                    if (actionsetId.equals(id)) {
						itemsToRemove.add(items[i]);
					}
                } else if (items[i] instanceof Separator) {
                    id = ((Separator) items[i]).getId();
                    if (actionsetId.equals(id)) {
						itemsToRemove.add(items[i]);
					}
                } else if (items[i] instanceof GroupMarker) {
                    id = ((GroupMarker) items[i]).getId();
                    if (actionsetId.equals(id)) {
						itemsToRemove.add(items[i]);
					}
                }
			}
            Iterator iter = itemsToRemove.iterator();
            while (iter.hasNext()) {
                IContributionItem item = (IContributionItem) iter.next();
                menuMgr.remove(item);
            }
            menuMgr.update(true);
        }

        private void revokeActionSetFromCoolbar(ICoolBarManager coolbarMgr,
                String actionsetId) {
            IContributionItem[] items = coolbarMgr.getItems();
            ArrayList itemsToRemove = new ArrayList();
            String id;
            for (int i = 0; i < items.length; i++) {
                id = items[i].getId();
                if (actionsetId.equals(id)) {
                    itemsToRemove.add(items[i]);
                    continue;
                }
                if (items[i] instanceof IToolBarManager) {
                    revokeActionSetFromToolbar((IToolBarManager) items[i],
                            actionsetId);
                } else if (items[i] instanceof IToolBarContributionItem) {
                    id = ((IToolBarContributionItem) items[i]).getId();
                    if (actionsetId.equals(id)) {
						itemsToRemove.add(items[i]);
					}
                } else if (items[i] instanceof GroupMarker) {
                    id = ((GroupMarker) items[i]).getId();
                    if (actionsetId.equals(id)) {
						itemsToRemove.add(items[i]);
					}
                }
            }
            Iterator iter = itemsToRemove.iterator();
            while (iter.hasNext()) {
				coolbarMgr.remove((IContributionItem) iter.next());
			}
            coolbarMgr.update(true);
        }

        private void revokeActionSetFromToolbar(IToolBarManager toolbarMgr,
                String actionsetId) {
            IContributionItem[] items = toolbarMgr.getItems();
            ArrayList itemsToRemove = new ArrayList();
            String id;
            for (int i = 0; i < items.length; i++) {
                id = items[i].getId();
                if (id.equals(actionsetId)) {
                    itemsToRemove.add(items[i]);
                    continue;
                }
                if (items[i] instanceof PluginActionCoolBarContributionItem) {
                    id = ((PluginActionCoolBarContributionItem) items[i])
                            .getActionSetId();
                    if (actionsetId.equals(id)) {
						itemsToRemove.add(items[i]);
					}
                } else if (items[i] instanceof ActionContributionItem) {
                    id = ((ActionContributionItem) items[i]).getId();
                    if (actionsetId.equals(id)) {
						itemsToRemove.add(items[i]);
					}
                } else if (items[i] instanceof GroupMarker) {
                    id = ((GroupMarker) items[i]).getId();
                    if (actionsetId.equals(id)) {
						itemsToRemove.add(items[i]);
					}
                }
            }
            Iterator iter = itemsToRemove.iterator();
            while (iter.hasNext()) {
				toolbarMgr.remove((IContributionItem) iter.next());
			}
            toolbarMgr.update(true);
        }
    }


    protected void removeActionExtensions(PluginActionSet set,
            IWorkbenchWindow window) {
        this.actionSet = set;
        this.window = window;
        currentContribution = null;
        targetID = null;
        targetContributionTag = IWorkbenchRegistryConstants.TAG_ACTION_SET;
        String id = set.getDesc().getId();
        
        if (cache != null) {
            for (int i = 0; i < cache.size(); i++) {
                ActionSetContribution contribution = (ActionSetContribution) cache
                        .get(i);
                contribution.revokeContribution((WorkbenchWindow) window,
                        actionSet.getBars(), id);
                if (contribution.isAdjunctContributor()) {
                    for (int j = 0; j < contribution.adjunctActions.size(); j++) {
                        ActionDescriptor adjunctAction = (ActionDescriptor) contribution.adjunctActions
                                .get(j);
                        contribution.revokeAdjunctCoolbarAction(adjunctAction,
                                actionSet.getBars());
                    }
                }
            }
        }
    }
}
