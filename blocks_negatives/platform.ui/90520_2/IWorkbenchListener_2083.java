/*******************************************************************************
 * Copyright (c) 2009-2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Marc R. Hoffmann <hoffmann@mountainminds.com>
 *         - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui;

/**
 * Constants for all commands defined by the Eclipse workbench.
 *
 * @since 3.5
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IWorkbenchCommandConstants {


    /**
     * Id for command "New" in category "File"
     * (value is <code>"org.eclipse.ui.newWizard"</code>).
     */

	/**
	 * Id for parameter "New Wizard" in command "New" in category "File" (value
	 * is <code>"newWizardId"</code>). Optional.
	 *
	 * @since 3.6
	 */

	/**
	 * Id for command "Close" in category "File" (value is
	 * <code>"org.eclipse.ui.file.close"</code>).
	 */

    /**
     * Id for command "Close All" in category "File"
     * (value is <code>"org.eclipse.ui.file.closeAll"</code>).
     */

    /**
     * Id for command "Import" in category "File"
     * (value is <code>"org.eclipse.ui.file.import"</code>).
     */

	/**
	 * Id for parameter "Import Wizard" in command "Import" in category "File"
	 * (value is <code>"importWizardId"</code>). Optional.
	 *
	 * @since 3.6
	 */

	/**
	 * Id for command "Export" in category "File" (value is
	 * <code>"org.eclipse.ui.file.export"</code>).
	 */

	/**
	 * Id for parameter "Export Wizard" in command "Export" in category "File"
	 * (value is <code>"exportWizardId"</code>). Optional.
	 *
	 * @since 3.6
	 */

    /**
     * Id for command "Save" in category "File"
     * (value is <code>"org.eclipse.ui.file.save"</code>).
     */

    /**
     * Id for command "Save As" in category "File"
     * (value is <code>"org.eclipse.ui.file.saveAs"</code>).
     */

    /**
     * Id for command "Save All" in category "File"
     * (value is <code>"org.eclipse.ui.file.saveAll"</code>).
     */

    /**
     * Id for command "Print" in category "File"
     * (value is <code>"org.eclipse.ui.file.print"</code>).
     */

    /**
     * Id for command "Revert" in category "File"
     * (value is <code>"org.eclipse.ui.file.revert"</code>).
     */

    /**
     * Id for command "Restart" in category "File"
     * (value is <code>"org.eclipse.ui.file.restartWorkbench"</code>).
     */

    /**
     * Id for command "Refresh" in category "File"
     * (value is <code>"org.eclipse.ui.file.refresh"</code>).
     */

    /**
     * Id for command "Properties" in category "File"
     * (value is <code>"org.eclipse.ui.file.properties"</code>).
     */

    /**
     * Id for command "Exit" in category "File"
     * (value is <code>"org.eclipse.ui.file.exit"</code>).
     */

    /**
     * Id for command "Move" in category "File"
     * (value is <code>"org.eclipse.ui.edit.move"</code>).
     */

    /**
     * Id for command "Rename" in category "File"
     * (value is <code>"org.eclipse.ui.edit.rename"</code>).
     */

    /**
     * Id for command "Close Others" in category "File"
     * (value is <code>"org.eclipse.ui.file.closeOthers"</code>).
     */


    /**
     * Id for command "Undo" in category "Edit"
     * (value is <code>"org.eclipse.ui.edit.undo"</code>).
     */

    /**
     * Id for command "Redo" in category "Edit"
     * (value is <code>"org.eclipse.ui.edit.redo"</code>).
     */

    /**
     * Id for command "Cut" in category "Edit"
     * (value is <code>"org.eclipse.ui.edit.cut"</code>).
     */

    /**
     * Id for command "Copy" in category "Edit"
     * (value is <code>"org.eclipse.ui.edit.copy"</code>).
     */

    /**
     * Id for command "Paste" in category "Edit"
     * (value is <code>"org.eclipse.ui.edit.paste"</code>).
     */

    /**
     * Id for command "Delete" in category "Edit"
     * (value is <code>"org.eclipse.ui.edit.delete"</code>).
     */

    /**
     * Id for command "Content Assist" in category "Edit"
     * (value is <code>"org.eclipse.ui.edit.text.contentAssist.proposals"</code>).
     */

    /**
     * Id for command "Context Information" in category "Edit"
     * (value is <code>"org.eclipse.ui.edit.text.contentAssist.contextInformation"</code>).
     */

    /**
     * Id for command "Select All" in category "Edit"
     * (value is <code>"org.eclipse.ui.edit.selectAll"</code>).
     */

    /**
     * Id for command "Find and Replace" in category "Edit"
     * (value is <code>"org.eclipse.ui.edit.findReplace"</code>).
     */

    /**
     * Id for command "Add Task" in category "Edit".
     * (value is <code>"org.eclipse.ui.edit.addTask"</code>).
     */

    /**
     * Id for command "Add Bookmark" in category "Edit"
     * (value is <code>"org.eclipse.ui.edit.addBookmark"</code>).
     */


    /**
     * Id for command "Go Into" in category "Navigate"
     * (value is <code>"org.eclipse.ui.navigate.goInto"</code>).
     */

    /**
     * Id for command "Back" in category "Navigate"
     * (value is <code>"org.eclipse.ui.navigate.back"</code>).
     */

    /**
     * Id for command "Forward" in category "Navigate"
     * (value is <code>"org.eclipse.ui.navigate.forward"</code>).
     */

    /**
     * Id for command "Up" in category "Navigate"
     * (value is <code>"org.eclipse.ui.navigate.up"</code>).
     */

    /**
     * Id for command "Next" in category "Navigate"
     * (value is <code>"org.eclipse.ui.navigate.next"</code>).
     */

    /**
     * Id for command "Backward History" in category "Navigate"
     * (value is <code>"org.eclipse.ui.navigate.backwardHistory"</code>).
     */

    /**
     * Id for command "Forward History" in category "Navigate"
     * (value is <code>"org.eclipse.ui.navigate.forwardHistory"</code>).
     */

    /**
     * Id for command "Previous" in category "Navigate"
     * (value is <code>"org.eclipse.ui.navigate.previous"</code>).
     */

    /**
     * Id for command "Toggle Link with Editor " in category "Navigate"
     * (value is <code>"org.eclipse.ui.navigate.linkWithEditor"</code>).
     */

    /**
     * Id for command "Next Page" in category "Navigate"
     * (value is <code>"org.eclipse.ui.part.nextPage"</code>).
     */

    /**
     * Id for command "Previous Page" in category "Navigate"
     * (value is <code>"org.eclipse.ui.part.previousPage"</code>).
     */

    /**
     * Id for command "Collapse All" in category "Navigate"
     * (value is <code>"org.eclipse.ui.navigate.collapseAll"</code>).
     */

	/**
	 * Id for command "Expand All" in category "Navigate" (value is
	 * <code>"org.eclipse.ui.navigate.expandAll"</code>).
	 *
	 * @since 3.6
	 */

    /**
     * Id for command "Show In" in category "Navigate"
     * (value is <code>"org.eclipse.ui.navigate.showIn"</code>).
     */

	/**
	 * Id for parameter "Target Id" in command "Show In" in category "Navigate"
	 * (value is <code>"org.eclipse.ui.navigate.showIn.targetId"</code>).
	 * Required.
	 *
	 * @since 3.6
	 */

	/**
	 * Id for command "Show In" in category "Navigate" (value is
	 * <code>"org.eclipse.ui.navigate.showInQuickMenu"</code>).
	 */


    /**
     * Id for command "Build All" in category "Project".
     * (value is <code>"org.eclipse.ui.project.buildAll"</code>).
     */

    /**
     * Id for command "Build Project" in category "Project".
     * (value is <code>"org.eclipse.ui.project.buildProject"</code>).
     */

    /**
     * Id for command "Close Project" in category "Project".
     * (value is <code>"org.eclipse.ui.project.closeProject"</code>).
     */

    /**
     * Id for command "Close Unrelated Projects" in category "Project".
     * (value is <code>"org.eclipse.ui.project.closeUnrelatedProjects"</code>).
     */

    /**
     * Id for command "Open Project" in category "Project".
     * (value is <code>"org.eclipse.ui.project.openProject"</code>).
     */


    /**
     * Id for command "New Window" in category "Window"
     * (value is <code>"org.eclipse.ui.window.newWindow"</code>).
     */

    /**
     * Id for command "New Editor" in category "Window"
     * (value is <code>"org.eclipse.ui.window.newEditor"</code>).
     */

    /**
     * Id for command "Show View Menu" in category "Window"
     * (value is <code>"org.eclipse.ui.window.showViewMenu"</code>).
     */

    /**
     * Id for command "Activate Editor" in category "Window"
     * (value is <code>"org.eclipse.ui.window.activateEditor"</code>).
     */

    /**
     * Id for command "Maximize Active View or Editor" in category "Window"
     * (value is <code>"org.eclipse.ui.window.maximizePart"</code>).
     */

    /**
     * Id for command "Minimize Active View or Editor" in category "Window"
     * (value is <code>"org.eclipse.ui.window.minimizePart"</code>).
     */

    /**
     * Id for command "Next Editor" in category "Window"
     * (value is <code>"org.eclipse.ui.window.nextEditor"</code>).
     */

    /**
     * Id for command "Previous Editor" in category "Window"
     * (value is <code>"org.eclipse.ui.window.previousEditor"</code>).
     */

    /**
     * Id for command "Next View" in category "Window"
     * (value is <code>"org.eclipse.ui.window.nextView"</code>).
     */

    /**
     * Id for command "Previous View" in category "Window"
     * (value is <code>"org.eclipse.ui.window.previousView"</code>).
     */

    /**
     * Id for command "Next Perspective" in category "Window"
     * (value is <code>"org.eclipse.ui.window.nextPerspective"</code>).
     */

    /**
     * Id for command "Previous Perspective" in category "Window"
     * (value is <code>"org.eclipse.ui.window.previousPerspective"</code>).
     */

    /**
     * Id for command "Close All Perspectives" in category "Window"
     * (value is <code>"org.eclipse.ui.window.closeAllPerspectives"</code>).
     */

    /**
     * Id for command "Close Perspective" in category "Window"
     * (value is <code>"org.eclipse.ui.window.closePerspective"</code>).
     */

	/**
	 * Id for parameter "Perspective Id" in command "Close Perspective" in
	 * category "Window" (value is
	 * <code>"org.eclipse.ui.window.closePerspective.perspectiveId"</code>).
	 * Optional.
	 *
	 * @since 3.6
	 */

	/**
	 * Id for command "Close Part" in category "Window" (value is
	 * <code>"org.eclipse.ui.file.closePart"</code>).
	 */

    /**
     * Id for command "Customize Perspective" in category "Window"
     * (value is <code>"org.eclipse.ui.window.customizePerspective"</code>).
     */

    /**
     * Id for command "Pin Editor" in category "Window"
     * (value is <code>"org.eclipse.ui.window.pinEditor"</code>).
     */

    /**
     * Id for command "Preferences" in category "Window"
     * (value is <code>"org.eclipse.ui.window.preferences"</code>).
     */

	/**
	 * Id for parameter "Preference Page Id" in command "Preferences" in
	 * category "Window" (value is <code>"preferencePageId"</code>). Optional.
	 *
	 * @since 3.6
	 */

	/**
	 * Id for command "Reset Perspective" in category "Window" (value is
	 * <code>"org.eclipse.ui.window.resetPerspective"</code>).
	 */

    /**
     * Id for command "Save Perspective As" in category "Window"
     * (value is <code>"org.eclipse.ui.window.savePerspective"</code>).
     */

    /**
     * Id for command "Show Key Assist" in category "Window"
     * (value is <code>"org.eclipse.ui.window.showKeyAssist"</code>).
     */

	/**
	 * Id for command "Lock Toolbar" in category "Window" (value is
	 * <code>"org.eclipse.ui.window.lockToolbar"</code>).
	 *
	 * @since 3.7
	 */


    /**
     * Id for command "Help Contents" in category "Help"
     * (value is <code>"org.eclipse.ui.help.helpContents"</code>).
     */

    /**
     * Id for command "Help Search" in category "Help"
     * (value is <code>"org.eclipse.ui.help.helpSearch"</code>).
     */

    /**
     * Id for command "Dynamic Help" in category "Help"
     * (value is <code>"org.eclipse.ui.help.dynamicHelp"</code>).
     */

    /**
     * Id for command "Welcome" in category "Help"
     * (value is <code>"org.eclipse.ui.help.quickStartAction"</code>).
     */

    /**
     * Id for command "Tips and Tricks" in category "Help"
     * (value is <code>"org.eclipse.ui.help.tipsAndTricksAction"</code>).
     */

    /**
     * Id for command "About" in category "Help"
     * (value is <code>"org.eclipse.ui.help.aboutAction"</code>).
     */


    /**
     * Id for command "Show View" in category "Views"
     * (value is <code>"org.eclipse.ui.views.showView"</code>).
     */

	/**
	 * Id for parameter "View Id" in command "Show View" in category "Views"
	 * (value is <code>"org.eclipse.ui.views.showView.viewId"</code>).
	 *
	 * @since 3.6
	 */

	/**
	 * Id for parameter "Secondary Id" in command "Show View" in category
	 * "Views" (value is
	 * <code>"org.eclipse.ui.views.showView.secondaryId"</code>).
	 *
	 * @since 3.7
	 */

	/**
	 * Id for parameter "As Fastview" in command "Show View" in category "Views"
	 * (value is <code>"org.eclipse.ui.views.showView.makeFast"</code>).
	 * Optional.
	 *
	 * @since 3.6
	 */


    /**
     * Id for command "Show Perspective" in category "Perspectives"
     * (value is <code>"org.eclipse.ui.perspectives.showPerspective"</code>).
     */

	/**
	 * Id for parameter "Perspective Id" in command "Show Perspective" in
	 * category "Perspectives" (value is
	 * <code>"org.eclipse.ui.perspectives.showPerspective.perspectiveId"</code>
	 * ).
	 *
	 * @since 3.6
	 */

	/**
	 * Id for parameter "In New Window" in command "Show Perspective" in
	 * category "Perspectives" (value is
	 * <code>"org.eclipse.ui.perspectives.showPerspective.newWindow"</code>).
	 * Optional.
	 *
	 * @since 3.6
	 */

}
