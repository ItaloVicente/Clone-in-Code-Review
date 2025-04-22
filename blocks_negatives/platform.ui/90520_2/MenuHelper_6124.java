/*******************************************************************************
 * Copyright (c) 2012, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.ui.internal.menus;

import java.util.ArrayList;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.ContributionsAnalyzer;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuContribution;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarContribution;
import org.eclipse.e4.ui.model.application.ui.menu.MTrimContribution;
import org.eclipse.e4.ui.model.application.ui.menu.impl.MenuFactoryImpl;
import org.eclipse.e4.ui.workbench.renderers.swt.ContributionRecord;
import org.eclipse.e4.ui.workbench.renderers.swt.ToolBarContributionRecord;
import org.eclipse.ui.internal.WorkbenchPlugin;

/**
 * @since 3.102.0
 *
 */
public class MenuFactoryGenerator {
	private MApplication application;
	private IConfigurationElement configElement;
	private MenuLocationURI location;

	public MenuFactoryGenerator(MApplication application, IEclipseContext appContext,
			IConfigurationElement configElement, String attribute) {
		this.application = application;
		assert appContext.equals(this.application.getContext());
		this.configElement = configElement;
		this.location = new MenuLocationURI(attribute);
	}

	private boolean inToolbar() {
	}

	public void mergeIntoModel(ArrayList<MMenuContribution> menuContributions,
			ArrayList<MToolBarContribution> toolBarContributions,
			ArrayList<MTrimContribution> trimContributions) {
		if (location.getPath() == null || location.getPath().length() == 0) {
			WorkbenchPlugin
			return;
		}
		if (inToolbar()) {
			if (MenuAdditionCacheEntry.isInWorkbenchTrim(location)) {
			} else {
				String query = location.getQuery();
				if (query == null || query.length() == 0) {
				}
				processToolbarChildren(toolBarContributions, configElement, location.getPath(),
						query);
			}
			return;
		}
		MMenuContribution menuContribution = MenuFactoryImpl.eINSTANCE.createMenuContribution();
		String idContrib = MenuHelper.getId(configElement);
		if (idContrib != null && idContrib.length() > 0) {
			menuContribution.setElementId(idContrib);
		}
		} else {
			menuContribution.setParentId(location.getPath());
		}
		String query = location.getQuery();
		if (query == null || query.length() == 0) {
		}
		menuContribution.setPositionInParent(query);
		String filter = ContributionsAnalyzer.MC_MENU;
			filter = ContributionsAnalyzer.MC_POPUP;
		}
		menuContribution.getTags().add(filter);
		menuContribution.setVisibleWhen(MenuHelper.getVisibleWhen(configElement));
		ContextFunction generator = new ContributionFactoryGenerator(configElement, 0);
		menuContribution.getTransientData().put(ContributionRecord.FACTORY, generator);
		menuContributions.add(menuContribution);
	}

	private void processToolbarChildren(ArrayList<MToolBarContribution> contributions,
			IConfigurationElement toolbar, String parentId, String position) {
		MToolBarContribution toolBarContribution = MenuFactoryImpl.eINSTANCE
				.createToolBarContribution();
		String idContrib = MenuHelper.getId(toolbar);
		if (idContrib != null && idContrib.length() > 0) {
			toolBarContribution.setElementId(idContrib);
		}
		toolBarContribution.setParentId(parentId);
		toolBarContribution.setPositionInParent(position);

		ContextFunction generator = new ContributionFactoryGenerator(configElement, 1);
		toolBarContribution.getTransientData().put(ToolBarContributionRecord.FACTORY, generator);

		contributions.add(toolBarContribution);
	}
}
