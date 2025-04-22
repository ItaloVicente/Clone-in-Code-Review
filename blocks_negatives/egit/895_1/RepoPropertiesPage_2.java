/*******************************************************************************
 * Copyright (C) 2009, Robin Rosenberg <robin.rosenberg@dewire.com>
 * Copyright (C) 2010, Ketan Padegaonkar <KetanPadegaonkar@gmail.com>
 * Copyright (C) 2010, Dariusz Luksza <dariusz@luksza.org>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *******************************************************************************/
package org.eclipse.egit.ui.common;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;

public class GitImportRepoWizard {

	private static final SWTWorkbenchBot bot = new SWTWorkbenchBot();

	public void openWizard() {
		bot.menu("File").menu("Import...").click();
		bot.shell("Import").activate();

		bot.tree().expandNode("Git").select("Projects from Git");

		bot.button("Next >").click();
	}

	public RepoPropertiesPage openCloneWizard() {
		bot.shell("Import Projects from Git").activate();

		bot.button("Clone...").click();

		bot.shell("Clone Git Repository").activate();

		return new RepoPropertiesPage();
	}

}
