/*******************************************************************************
 * Copyright (c) 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.e4.ui.workbench.addons.swt;

import java.util.List;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MAddon;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.impl.ApplicationFactoryImpl;

public class SplitterProcessor {
	@Execute
	void addSplitterAddon(MApplication app) {
		List<MAddon> addons = app.getAddons();

		for (MAddon addon : addons) {
			if (addon.getContributionURI().contains(
					"ui.workbench.addons.splitteraddon.SplitterAddon"))
				return;
		}

		MAddon splitterAddon = ApplicationFactoryImpl.eINSTANCE.createAddon();
		splitterAddon
		app.getAddons().add(splitterAddon);
	}
}
