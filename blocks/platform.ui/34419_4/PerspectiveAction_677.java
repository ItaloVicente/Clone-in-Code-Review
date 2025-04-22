
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
		alwaysOnActionSets = new ArrayList<IActionSetDescriptor>(2);
		alwaysOffActionSets = new ArrayList<IActionSetDescriptor>(2);
	}

	public void initActionSets() {
		if (descriptor != null) {
			List<String> alwaysOn = ModeledPageLayout.getIds(layout, ModeledPageLayout.ACTION_SET_TAG);

			String hiddenIDs = page.getHiddenItems();
			List<String> alwaysOff = new ArrayList<String>();

			String[] hiddenIds = hiddenIDs.split(","); //$NON-NLS-1$
			for (String id : hiddenIds) {
				if (!id.startsWith(ModeledPageLayout.HIDDEN_ACTIONSET_PREFIX)) {
					continue;
				}
				id = id.substring(ModeledPageLayout.HIDDEN_ACTIONSET_PREFIX.length());
				if (!alwaysOff.contains(id)) {
					alwaysOff.add(id);
				}
			}

			alwaysOn.removeAll(alwaysOff);

			for (IActionSetDescriptor descriptor : createInitialActionSets(alwaysOn)) {
				if (!alwaysOnActionSets.contains(descriptor)) {
					alwaysOnActionSets.add(descriptor);
				}
			}

			for (IActionSetDescriptor descriptor : createInitialActionSets(alwaysOff)) {
				if (!alwaysOffActionSets.contains(descriptor)) {
					alwaysOffActionSets.add(descriptor);
				}
			}
		}

	}

	private List<IActionSetDescriptor> createInitialActionSets(List<String> ids) {
		List<IActionSetDescriptor> result = new ArrayList<IActionSetDescriptor>();
		ActionSetRegistry reg = WorkbenchPlugin.getDefault().getActionSetRegistry();
		for (String id : ids) {
			IActionSetDescriptor desc = reg.findActionSet(id);
			if (desc != null) {
				result.add(desc);
			} else {
			}
		}
		return result;
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

	public String[] getShowViewShortcuts() {
		return page.getShowViewShortcuts();
	}

	private void removeAlwaysOn(IActionSetDescriptor descriptor) {
		if (alwaysOnActionSets.contains(descriptor)) {
			alwaysOnActionSets.remove(descriptor);
			page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_HIDE);
		}
	}

	private void addAlwaysOff(IActionSetDescriptor descriptor) {
		if (!alwaysOffActionSets.contains(descriptor)) {
			alwaysOffActionSets.add(descriptor);
			page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_MASK);
			removeAlwaysOn(descriptor);
		}
	}

	private void addAlwaysOn(IActionSetDescriptor descriptor) {
		if (!alwaysOnActionSets.contains(descriptor)) {
			alwaysOnActionSets.add(descriptor);
			page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_SHOW);
			removeAlwaysOff(descriptor);
		}
	}

	private void removeAlwaysOff(IActionSetDescriptor descriptor) {
		if (alwaysOffActionSets.contains(descriptor)) {
			alwaysOffActionSets.remove(descriptor);
			page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_UNMASK);
		}
	}

	public void turnOnActionSets(IActionSetDescriptor[] newArray) {
		for (IActionSetDescriptor descriptor : newArray) {
			addActionSet(descriptor);
		}
	}

	public void turnOffActionSets(IActionSetDescriptor[] toDisable) {
		for (IActionSetDescriptor descriptor : toDisable) {
			turnOffActionSet(descriptor);
		}
	}

	public void turnOffActionSet(IActionSetDescriptor toDisable) {
		removeActionSet(toDisable);
	}

	protected void addActionSet(IActionSetDescriptor newDesc) {
		IContextService service = page.getWorkbenchWindow().getService(IContextService.class);
		try {
			service.deferUpdates(true);
			for (IActionSetDescriptor desc : alwaysOnActionSets) {
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

			for (IActionSetDescriptor desc : alwaysOnActionSets) {
				if (desc.getId().equals(id)) {
					removeAlwaysOn(desc);
					break;
				}
			}

			for (IActionSetDescriptor desc : alwaysOffActionSets) {
				if (desc.getId().equals(id)) {
					removeAlwaysOff(desc);
					break;
				}
			}
			addAlwaysOff(toRemove);
		} finally {
			service.deferUpdates(false);
		}
	}

	public List<IActionSetDescriptor> getAlwaysOnActionSets() {
		return alwaysOnActionSets;
	}

	public List<IActionSetDescriptor> getAlwaysOffActionSets() {
		return alwaysOffActionSets;
	}

	public void updateActionBars() {
		page.getActionBars().getMenuManager().updateAll(true);
		page.resetToolBarLayout();
	}

}
