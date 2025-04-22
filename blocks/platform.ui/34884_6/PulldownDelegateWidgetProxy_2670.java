
package org.eclipse.ui.internal.menus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.regex.Pattern;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryChangeEvent;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.ContributionsAnalyzer;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuContribution;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarContribution;
import org.eclipse.e4.ui.model.application.ui.menu.MTrimContribution;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.services.RegistryPersistence;

final public class MenuPersistence extends RegistryPersistence {

	private MApplication application;
	private IEclipseContext appContext;
	private ArrayList<MenuAdditionCacheEntry> cacheEntries = new ArrayList<MenuAdditionCacheEntry>();

	private ArrayList<MMenuContribution> menuContributions = new ArrayList<MMenuContribution>();
	private ArrayList<MToolBarContribution> toolBarContributions = new ArrayList<MToolBarContribution>();
	private ArrayList<MTrimContribution> trimContributions = new ArrayList<MTrimContribution>();

	private final Comparator<IConfigurationElement> comparer = new Comparator<IConfigurationElement>() {
		@Override
		public int compare(IConfigurationElement c1, IConfigurationElement c2) {
			return c1.getContributor().getName().compareToIgnoreCase(c2.getContributor().getName());
		}
	};
	private Pattern contributorFilter;

	public MenuPersistence(MApplication application, IEclipseContext appContext) {
		this.application = application;
		this.appContext = appContext;
	}

	public MenuPersistence(MApplication application, IEclipseContext appContext, String filterRegex) {
		this(application, appContext);
		contributorFilter = Pattern.compile(filterRegex);
	}

	@Override
	public void dispose() {
		ControlContributionRegistry.clear();
		application.getMenuContributions().removeAll(menuContributions);
		application.getToolBarContributions().removeAll(toolBarContributions);
		application.getTrimContributions().removeAll(trimContributions);
		menuContributions.clear();
		cacheEntries.clear();
		super.dispose();
	}
	@Override
	protected boolean isChangeImportant(IRegistryChangeEvent event) {
		return false;
	}

	public void reRead() {
		read();
	}

	@Override
	protected final void read() {
		super.read();

		readAdditions();

		ArrayList<MMenuContribution> tmp = new ArrayList<MMenuContribution>(menuContributions);
		menuContributions.clear();
		ContributionsAnalyzer.mergeContributions(tmp, menuContributions);
		application.getMenuContributions().addAll(menuContributions);

		ArrayList<MToolBarContribution> tmpToolbar = new ArrayList<MToolBarContribution>(
				toolBarContributions);
		toolBarContributions.clear();
		ContributionsAnalyzer.mergeToolBarContributions(tmpToolbar, toolBarContributions);
		application.getToolBarContributions().addAll(toolBarContributions);

		ArrayList<MTrimContribution> tmpTrim = new ArrayList<MTrimContribution>(trimContributions);
		trimContributions.clear();
		ContributionsAnalyzer.mergeTrimContributions(tmpTrim, trimContributions);
		application.getTrimContributions().addAll(trimContributions);
	}

	private void readAdditions() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		ArrayList<IConfigurationElement> configElements = new ArrayList<IConfigurationElement>();

		final IConfigurationElement[] menusExtensionPoint = registry
				.getConfigurationElementsFor(EXTENSION_MENUS);

		for (int i = 0; i < menusExtensionPoint.length; i++) {
			if (PL_MENU_CONTRIBUTION.equals(menusExtensionPoint[i].getName())) {
				if (contributorFilter == null
						|| contributorFilter.matcher(
								menusExtensionPoint[i].getContributor().getName()).matches()) {
					configElements.add(menusExtensionPoint[i]);
				}
			}
		}
		Collections.sort(configElements, comparer);
		Iterator<IConfigurationElement> i = configElements.iterator();
		while (i.hasNext()) {
			final IConfigurationElement configElement = i.next();

			if (isProgramaticContribution(configElement)) {
				MenuFactoryGenerator gen = new MenuFactoryGenerator(application, appContext,
						configElement,
						configElement.getAttribute(IWorkbenchRegistryConstants.TAG_LOCATION_URI));
				gen.mergeIntoModel(menuContributions, toolBarContributions, trimContributions);
			} else {
				MenuAdditionCacheEntry menuContribution = new MenuAdditionCacheEntry(application,
						appContext, configElement,
						configElement.getAttribute(IWorkbenchRegistryConstants.TAG_LOCATION_URI),
						configElement.getNamespaceIdentifier());
				cacheEntries.add(menuContribution);
				menuContribution.mergeIntoModel(menuContributions, toolBarContributions,
						trimContributions);
			}
		}
	}


	private boolean isProgramaticContribution(IConfigurationElement menuAddition) {
		return menuAddition.getAttribute(IWorkbenchRegistryConstants.ATT_CLASS) != null;
	}
}
