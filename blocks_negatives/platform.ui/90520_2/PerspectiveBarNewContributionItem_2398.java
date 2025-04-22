/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Markus Alexander Kuppe, Versant GmbH - bug 215797
 *     Sascha Zak - bug 282874
 *     Lars Vogel <Lars.Vogel@vogella.com> - Bug 440810, 440136, 472654
 *     Andrey Loskutov <loskutov@gmx.de> - Bug 404348, 421178, 456727
 *******************************************************************************/

package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.Assert;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.internal.e4.compatibility.ModeledPageLayout;
import org.eclipse.ui.internal.registry.ActionSetRegistry;
import org.eclipse.ui.internal.registry.IActionSetDescriptor;
import org.eclipse.ui.internal.registry.PerspectiveDescriptor;

/**
 *
 */
public class Perspective {

	private final PerspectiveDescriptor descriptor;
	private final WorkbenchPage page;
	private final List<IActionSetDescriptor> alwaysOnActionSets;
	private final List<IActionSetDescriptor> alwaysOffActionSets;
	private final MPerspective layout;

	public Perspective(PerspectiveDescriptor desc, MPerspective layout, WorkbenchPage page) {
		Assert.isNotNull(page);
		this.page = page;
		this.layout = layout;
		descriptor = desc;
		alwaysOnActionSets = new ArrayList<>(2);
		alwaysOffActionSets = new ArrayList<>(2);
	}

	public void initActionSets() {
		if (descriptor != null) {
			List<String> alwaysOn = ModeledPageLayout.getIds(layout, ModeledPageLayout.ACTION_SET_TAG);

			String hiddenIDs = page.getHiddenItems();
			List<String> alwaysOff = new ArrayList<>();

			String[] hiddenIds = hiddenIDs.split(",); //$NON-NLS-1$
-			for (String id : hiddenIds) {
-				if (!id.startsWith(ModeledPageLayout.HIDDEN_ACTIONSET_PREFIX)) {
-					continue;
-				}
-				id = id.substring(ModeledPageLayout.HIDDEN_ACTIONSET_PREFIX.length());
-				if (!alwaysOff.contains(id)) {
-					alwaysOff.add(id);
-				}
-			}
-
-			alwaysOn.removeAll(alwaysOff);
-
-			for (IActionSetDescriptor descriptor : createInitialActionSets(alwaysOn)) {
-				if (!alwaysOnActionSets.contains(descriptor)) {
-					alwaysOnActionSets.add(descriptor);
-				}
-			}
-
-			for (IActionSetDescriptor descriptor : createInitialActionSets(alwaysOff)) {
-				if (!alwaysOffActionSets.contains(descriptor)) {
-					alwaysOffActionSets.add(descriptor);
-				}
-			}
-		}
-
-	}
-
-	/**
-	 * Create the initial list of action sets.
-	 *
-	 * @return action set descriptors created from given descriptor id's, can be
-	 *         empty but never null.
-	 */
-	private List<IActionSetDescriptor> createInitialActionSets(List<String> ids) {
-		List<IActionSetDescriptor> result = new ArrayList<>();
-		ActionSetRegistry reg = WorkbenchPlugin.getDefault().getActionSetRegistry();
-		for (String id : ids) {
-			IActionSetDescriptor desc = reg.findActionSet(id);
-			if (desc != null) {
-				result.add(desc);
-			} else {
-				// plugin with actionSet was removed
-				// we remember then so it's available when added back
-			}
-		}
-		return result;
-	}
-
-	/**
-	 * Returns the perspective.
-	 *
-	 * @return can return null!
-	 */
-	public IPerspectiveDescriptor getDesc() {
-		return descriptor;
-	}
-
-	/**
-	 * Returns the new wizard shortcuts associated with this perspective.
-	 *
-	 * @return an array of new wizard identifiers
-	 */
-	public String[] getNewWizardShortcuts() {
-		return page.getNewWizardShortcuts();
-	}
-
-	/**
-	 * Returns the perspective shortcuts associated with this perspective.
-	 *
-	 * @return an array of perspective identifiers
-	 */
-	public String[] getPerspectiveShortcuts() {
-		return page.getPerspectiveShortcuts();
-	}
-
-	/**
-	 * Returns the show view shortcuts associated with this perspective.
-	 *
-	 * @return an array of view identifiers
-	 */
-	public String[] getShowViewShortcuts() {
-		return page.getShowViewShortcuts();
-	}
-
-	private void removeAlwaysOn(IActionSetDescriptor descriptor) {
-		if (alwaysOnActionSets.contains(descriptor)) {
-			alwaysOnActionSets.remove(descriptor);
-			page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_HIDE);
-		}
-	}
-
-	private void addAlwaysOff(IActionSetDescriptor descriptor) {
-		if (!alwaysOffActionSets.contains(descriptor)) {
-			alwaysOffActionSets.add(descriptor);
-			page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_MASK);
-			removeAlwaysOn(descriptor);
-		}
-	}
-
-	private void addAlwaysOn(IActionSetDescriptor descriptor) {
-		if (!alwaysOnActionSets.contains(descriptor)) {
-			alwaysOnActionSets.add(descriptor);
-			page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_SHOW);
-			removeAlwaysOff(descriptor);
-		}
-	}
-
-	private void removeAlwaysOff(IActionSetDescriptor descriptor) {
-		if (alwaysOffActionSets.contains(descriptor)) {
-			alwaysOffActionSets.remove(descriptor);
-			page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_UNMASK);
-		}
-	}
-
-	public void turnOnActionSets(IActionSetDescriptor[] newArray) {
-		for (IActionSetDescriptor descriptor : newArray) {
-			addActionSet(descriptor);
-		}
-	}
-
-	public void turnOffActionSets(IActionSetDescriptor[] toDisable) {
-		for (IActionSetDescriptor descriptor : toDisable) {
-			turnOffActionSet(descriptor);
-		}
-	}
-
-	public void turnOffActionSet(IActionSetDescriptor toDisable) {
-		removeActionSet(toDisable);
-	}
-
-	// for dynamic UI
-	protected void addActionSet(IActionSetDescriptor newDesc) {
-		IContextService service = page.getWorkbenchWindow().getService(IContextService.class);
-		try {
-			service.deferUpdates(true);
-			for (IActionSetDescriptor desc : alwaysOnActionSets) {
-				if (desc.getId().equals(newDesc.getId())) {
-					removeAlwaysOn(desc);
-					removeAlwaysOff(desc);
-					break;
-				}
-			}
-			addAlwaysOn(newDesc);
-			final String actionSetID = newDesc.getId();
-
-			// Add Tags
-			String tag = ModeledPageLayout.ACTION_SET_TAG + actionSetID;
-			if (!layout.getTags().contains(tag)) {
-				layout.getTags().add(tag);
-			}
-		} finally {
-			service.deferUpdates(false);
-		}
-	}
-
-	// for dynamic UI
-	protected void removeActionSet(IActionSetDescriptor toRemove) {
-		String id = toRemove.getId();
-		IContextService service = page.getWorkbenchWindow().getService(IContextService.class);
-		try {
-			service.deferUpdates(true);
-
-			// this advance for loop only works because it breaks out of it
-			// right after the removal
-			for (IActionSetDescriptor desc : alwaysOnActionSets) {
-				if (desc.getId().equals(id)) {
-					removeAlwaysOn(desc);
-					break;
-				}
-			}
-
-			// this advance for loop only works because it breaks out of it
-			// right after the removal
-			for (IActionSetDescriptor desc : alwaysOffActionSets) {
-				if (desc.getId().equals(id)) {
-					removeAlwaysOff(desc);
-					break;
-				}
-			}
-			addAlwaysOff(toRemove);
-			// not necessary to remove the ModeledPageLayout.ACTION_SET_TAG + id
-			// tag as the entry is only disabled.
-		} finally {
-			service.deferUpdates(false);
-		}
-	}
-
-	public List<IActionSetDescriptor> getAlwaysOnActionSets() {
-		return alwaysOnActionSets;
-	}
-
-	public List<IActionSetDescriptor> getAlwaysOffActionSets() {
-		return alwaysOffActionSets;
-	}
-
-	public void updateActionBars() {
-		page.getActionBars().getMenuManager().updateAll(true);
-		page.resetToolBarLayout();
-	}
-
-}
diff --git a/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/internal/PerspectiveAction.java b/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/internal/PerspectiveAction.java
deleted file mode 100644
index 613e6821cb..0000000000
--- a/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/internal/PerspectiveAction.java	
+++ /dev/null
@@ -1,91 +0,0 @@
-/*******************************************************************************
- * Copyright (c) 2005, 2015 IBM Corporation and others.
- * All rights reserved. This program and the accompanying materials
- * are made available under the terms of the Eclipse Public License v1.0
- * which accompanies this distribution, and is available at
- * http://www.eclipse.org/legal/epl-v10.html
- *
- * Contributors:
- *     IBM Corporation - initial API and implementation
- *******************************************************************************/
-
-package org.eclipse.ui.internal;
-
-import org.eclipse.core.runtime.Assert;
-import org.eclipse.jface.action.Action;
-import org.eclipse.ui.IPerspectiveDescriptor;
-import org.eclipse.ui.IWorkbenchPage;
-import org.eclipse.ui.IWorkbenchWindow;
-import org.eclipse.ui.actions.ActionFactory;
-
-/**
- * Abstract superclass of actions which are enabled iff there is an active perspective
- * in the window.
- *
- * @since 3.1
- */
-public abstract class PerspectiveAction extends Action implements ActionFactory.IWorkbenchAction {
-
-    /**
-     * The workbench window containing this action.
-     */
-    private IWorkbenchWindow workbenchWindow;
-
-    /**
-     * Tracks perspective activation, to update this action's
-     * enabled state.
-     */
-    private PerspectiveTracker tracker;
-
-    /**
-     * Constructs a new perspective action for the given window.
-     *
-     * @param window the window
-     */
-    protected PerspectiveAction(IWorkbenchWindow window) {
-        Assert.isNotNull(window);
-        this.workbenchWindow = window;
-        tracker = new PerspectiveTracker(window, this);
-    }
-
-    /**
-     * Returns the window, or <code>null</code> if the action has been disposed.
-     *
-     * @return the window or <code>null</code>
-     */
-    protected IWorkbenchWindow getWindow() {
-        return workbenchWindow;
-    }
-
-    @Override
-	public void run() {
-        if (workbenchWindow == null) {
-            // action has been disposed
-            return;
-        }
-        IWorkbenchPage page = workbenchWindow.getActivePage();
-        if (page != null && page.getPerspective() != null) {
-            run(page, page.getPerspective());
-        }
-    }
-
-    /**
-     * Runs the action, passing the active page and perspective.
-     *
-     * @param page the active page
-     * @param persp the active perspective
-     */
-    protected abstract void run(IWorkbenchPage page, IPerspectiveDescriptor persp);
-
-    @Override
-	public void dispose() {
-        if (workbenchWindow == null) {
-            // already disposed
-            return;
-        }
-        tracker.dispose();
-        workbenchWindow = null;
-    }
-
-}
-
diff --git a/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/internal/PerspectiveBarContributionItem.java b/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/internal/PerspectiveBarContributionItem.java
deleted file mode 100644
index cef6677784..0000000000
--- a/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/internal/PerspectiveBarContributionItem.java	
+++ /dev/null
@@ -1,232 +0,0 @@
-/*******************************************************************************
- * Copyright (c) 2000, 2015 IBM Corporation and others.
- * All rights reserved. This program and the accompanying materials
- * are made available under the terms of the Eclipse Public License v1.0
- * which accompanies this distribution, and is available at
- * http://www.eclipse.org/legal/epl-v10.html
- *
- * Contributors:
- *     IBM Corporation - initial API and implementation
- *******************************************************************************/
-package org.eclipse.ui.internal;
-
-import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;
-
-import org.eclipse.jface.action.ContributionItem;
-import org.eclipse.jface.preference.IPreferenceStore;
-import org.eclipse.jface.resource.ImageDescriptor;
-import org.eclipse.osgi.util.NLS;
-import org.eclipse.swt.SWT;
-import org.eclipse.swt.graphics.GC;
-import org.eclipse.swt.graphics.Image;
-import org.eclipse.swt.widgets.ToolBar;
-import org.eclipse.swt.widgets.ToolItem;
-import org.eclipse.ui.IPerspectiveDescriptor;
-import org.eclipse.ui.ISharedImages;
-import org.eclipse.ui.IWorkbenchPage;
-import org.eclipse.ui.IWorkbenchPreferenceConstants;
-import org.eclipse.ui.internal.util.PrefUtil;
-
-/**
- * A  {@link ContributionItem} specifically for contributions to the perspective switcher.
- *
- */
-public class PerspectiveBarContributionItem extends ContributionItem {
-
-    private IPerspectiveDescriptor perspective;
-
-    private IPreferenceStore apiPreferenceStore = PrefUtil
-            .getAPIPreferenceStore();
-
-    private ToolItem toolItem = null;
-
-    private Image image;
-
-    private IWorkbenchPage workbenchPage;
-
-    /**
-     * Create a new perspective contribution item
-     *
-     * @param perspective the descriptor for the perspective
-     * @param workbenchPage the page that this perspective is in
-     */
-    public PerspectiveBarContributionItem(IPerspectiveDescriptor perspective,
-            IWorkbenchPage workbenchPage) {
-        super(perspective.getId());
-        this.perspective = perspective;
-        this.workbenchPage = workbenchPage;
-    }
-
-    @Override
-	public void dispose() {
-        super.dispose();
-        if (image != null && !image.isDisposed()) {
-            image.dispose();
-            image = null;
-        }
-        apiPreferenceStore = null;
-        workbenchPage = null;
-        perspective = null;
-
-    }
-
-    @Override
-	public void fill(ToolBar parent, int index) {
-        if (toolItem == null && parent != null && !parent.isDisposed()) {
-
-            if (index >= 0) {
-				toolItem = new ToolItem(parent, SWT.CHECK, index);
-			} else {
-				toolItem = new ToolItem(parent, SWT.CHECK);
-			}
-
-            if (image == null || image.isDisposed()) {
-                createImage();
-            }
-            toolItem.setImage(image);
-
-            toolItem.setToolTipText(NLS.bind(WorkbenchMessages.PerspectiveBarContributionItem_toolTip, perspective.getLabel()));
-            toolItem.addSelectionListener(widgetSelectedAdapter(event -> select()));
-            toolItem.setData(this); //TODO review need for this
-            update();
-        }
-    }
-
-    private void createImage() {
-        ImageDescriptor imageDescriptor = perspective.getImageDescriptor();
-        if (imageDescriptor != null) {
-            image = imageDescriptor.createImage();
-        } else {
-            image = WorkbenchImages.getImageDescriptor(
-                    ISharedImages.IMG_ETOOL_DEF_PERSPECTIVE)
-                    .createImage();
-        }
-    }
-
-    Image getImage() {
-        if (image == null) {
-            createImage();
-        }
-        return image;
-    }
-
-    /**
-     * Select this perspective
-     */
-    public void select() {
-        if (workbenchPage.getPerspective() != perspective) {
-            workbenchPage.setPerspective(perspective);
-        } else {
-			toolItem.setSelection(true);
-		}
-    }
-
-    @Override
-	public void update() {
-        if (toolItem != null && !toolItem.isDisposed()) {
-            toolItem
-                    .setSelection(workbenchPage.getPerspective() == perspective);
-            if (apiPreferenceStore
-                    .getBoolean(IWorkbenchPreferenceConstants.SHOW_TEXT_ON_PERSPECTIVE_BAR)) {
-                if (apiPreferenceStore.getString(
-                        IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR)
-                        .equals(IWorkbenchPreferenceConstants.TOP_LEFT)) {
-					toolItem.setText(perspective.getLabel());
-				} else {
-					toolItem.setText(shortenText(perspective.getLabel(),
-                            toolItem));
-				}
-            } else {
-                toolItem.setText("); //$NON-NLS-1$
-            }
-        }
-    }
-
-    /**
-     * Update this item with a new perspective descriptor
-     * @param newDesc
-     */
-    public void update(IPerspectiveDescriptor newDesc) {
-        perspective = newDesc;
-        if (toolItem != null && !toolItem.isDisposed()) {
-            ImageDescriptor imageDescriptor = perspective.getImageDescriptor();
-            if (imageDescriptor != null) {
-                toolItem.setImage(imageDescriptor.createImage());
-            } else {
-                toolItem.setImage(WorkbenchImages.getImageDescriptor(
-                        ISharedImages.IMG_ETOOL_DEF_PERSPECTIVE)
-                        .createImage());
-            }
-            toolItem.setToolTipText(NLS.bind(WorkbenchMessages.PerspectiveBarContributionItem_toolTip, perspective.getLabel() ));
-        }
-        update();
-    }
-
-    IWorkbenchPage getPage() {
-        return workbenchPage;
-    }
-
-    IPerspectiveDescriptor getPerspective() {
-        return perspective;
-    }
-
-    ToolItem getToolItem() {
-        return toolItem;
-    }
-
-    /**
-     * Answer whether the receiver is a match for the provided
-     * perspective descriptor
-     *
-     * @param perspective the perspective descriptor
-     * @param workbenchPage the page
-     * @return <code>true</code> if it is a match
-     */
-    public boolean handles(IPerspectiveDescriptor perspective,
-            IWorkbenchPage workbenchPage) {
-        return this.perspective == perspective
-                && this.workbenchPage == workbenchPage;
-    }
-
-    /**
-     * Set the current perspective
-     * @param newPerspective
-     */
-    public void setPerspective(IPerspectiveDescriptor newPerspective) {
-        this.perspective = newPerspective;
-    }
-
-    // TODO review need for this method
-    void setSelection(boolean b) {
-        if (toolItem != null && !toolItem.isDisposed()) {
-			toolItem.setSelection(b);
-		}
-    }
-
-    static int getMaxWidth(Image image) {
-        return image.getBounds().width * 5;
-    }
-
-    private static final String ellipsis = ..."; //$NON-NLS-1$

    protected String shortenText(String textValue, ToolItem item) {
        if (textValue == null || toolItem == null || toolItem.isDisposed()) {
			return null;
		}
        String returnText = textValue;
        GC gc = new GC(item.getParent());
        int maxWidth = getMaxWidth(item.getImage());
        if (gc.textExtent(textValue).x >= maxWidth) {
            for (int i = textValue.length(); i > 0; i--) {
                String test = textValue.substring(0, i);
                test = test + ellipsis;
                if (gc.textExtent(test).x < maxWidth) {
                    returnText = test;
                    break;
                }
            }
        }
        gc.dispose();
        return returnText;
    }
}
