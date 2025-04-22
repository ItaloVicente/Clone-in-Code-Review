
package org.eclipse.ui.internal.menus;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.menus.IWorkbenchContribution;
import org.eclipse.ui.services.IServiceLocator;

public class DynamicMenuContributionItem extends ContributionItem {

	private final IConfigurationElement dynamicAddition;
	private final IServiceLocator locator;
	private boolean alreadyFailed;
	private ContributionItem loadedDynamicContribution;

	public DynamicMenuContributionItem(String id, IServiceLocator locator,
			IConfigurationElement dynamicAddition) {
		super(id);

		this.locator = locator;
		this.dynamicAddition = dynamicAddition;
	}

	@Override
	public boolean isDynamic() {
		if (loadedDynamicContribution != null) {
			return loadedDynamicContribution.isDynamic();
		}
		return true;
	}

	@Override
	public boolean isDirty() {
		if (loadedDynamicContribution != null) {
			return loadedDynamicContribution.isDirty();
		}
		return super.isDirty();
	}

	@Override
	public boolean isEnabled() {
		if (loadedDynamicContribution != null) {
			return loadedDynamicContribution.isEnabled();
		}
		return super.isEnabled();
	}

	@Override
	public boolean isGroupMarker() {
		if (loadedDynamicContribution != null) {
			return loadedDynamicContribution.isGroupMarker();
		}
		return super.isGroupMarker();
	}

	@Override
	public boolean isSeparator() {
		if (loadedDynamicContribution != null) {
			return loadedDynamicContribution.isSeparator();
		}
		return super.isSeparator();
	}

	@Override
	public boolean isVisible() {
		if (loadedDynamicContribution != null) {
			return loadedDynamicContribution.isVisible();
		}
		return super.isVisible();
	}

	@Override
	public void saveWidgetState() {
		if (loadedDynamicContribution != null) {
			loadedDynamicContribution.saveWidgetState();
		}
		super.saveWidgetState();
	}

	@Override
	public void setVisible(boolean visible) {
		if (loadedDynamicContribution != null) {
			loadedDynamicContribution.setVisible(visible);
		}
		super.setVisible(visible);
	}

	@Override
	public void fill(Composite parent) {
		IContributionItem contributionItem = getContributionItem();
		if (contributionItem != null)
			contributionItem.fill(parent);
	}

	@Override
	public void fill(CoolBar parent, int index) {
		IContributionItem contributionItem = getContributionItem();
		if (contributionItem != null)
			contributionItem.fill(parent, index);
	}

	@Override
	public void fill(Menu menu, int index) {
		IContributionItem contributionItem = getContributionItem();
		if (contributionItem != null)
			contributionItem.fill(menu, index);
	}

	@Override
	public void fill(ToolBar parent, int index) {
		IContributionItem contributionItem = getContributionItem();
		if (contributionItem != null)
			contributionItem.fill(parent, index);
	}

	private IContributionItem getContributionItem() {
		if (loadedDynamicContribution == null && !alreadyFailed)
			createContributionItem();
		return loadedDynamicContribution;
	}

	private void createContributionItem() {

		loadedDynamicContribution = (ContributionItem) Util
				.safeLoadExecutableExtension(dynamicAddition,
						IWorkbenchRegistryConstants.ATT_CLASS,
						ContributionItem.class);

		if (loadedDynamicContribution == null) {
			alreadyFailed = true;
			return;
		}

		loadedDynamicContribution.setId(getId());
		loadedDynamicContribution.setParent(getParent());
		if (loadedDynamicContribution instanceof IWorkbenchContribution) {
			((IWorkbenchContribution) loadedDynamicContribution)
					.initialize(locator);
		}
	}

	@Override
	public void dispose() {
		if (loadedDynamicContribution != null) {
			loadedDynamicContribution.dispose();
			loadedDynamicContribution = null;
		}
		super.dispose();
	}

	@Override
	public void update() {
		if (loadedDynamicContribution != null) {
			loadedDynamicContribution.update();
		}
	}

	@Override
	public void update(String id) {
		if (loadedDynamicContribution != null) {
			loadedDynamicContribution.update(id);
		}
	}

	@Override
	public void setParent(IContributionManager parent) {
		super.setParent(parent);
		if (loadedDynamicContribution != null) {
			loadedDynamicContribution.setParent(parent);
		}
	}

}
