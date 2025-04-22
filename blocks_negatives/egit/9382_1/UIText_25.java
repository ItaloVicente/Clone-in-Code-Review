/*******************************************************************************
 * Copyright (C) 2007, Robin Rosenberg <robin.rosenberg@dewire.com>
 * Copyright (C) 2008, Roger C. Soares <rogersoares@intelinet.com.br>
 * Copyright (C) 2007, Shawn O. Pearce <spearce@spearce.org>
 * Copyright (C) 2010, Chris Aniszczyk <caniszczyk@gmail.com>
 * Copyright (C) 2013, Daniel Megert <daniel_megert@ch.ibm.com>
 * Copyright (C) 2013, Robin Stocker <robin@nibor.org>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *******************************************************************************/
package org.eclipse.egit.ui.internal;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.egit.ui.Activator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.graphics.Image;

/**
 * Icons for the the Eclipse plugin. Mostly decorations.
 */
public class UIIcons {

	/** Decoration for resource in the index but not yet committed. */
	public final static ImageDescriptor OVR_STAGED;

	/** Decoration for resource added to index but not yet committed. */
	public final static ImageDescriptor OVR_STAGED_ADD;

	/** Decoration for resource removed from the index but not commit. */
	public final static ImageDescriptor OVR_STAGED_REMOVE;

	/** Decoration for resource that was removed and added with another name */
	public static final ImageDescriptor OVR_STAGED_RENAME;

	/** Decoration for resource not being tracked by Git */
	public final static ImageDescriptor OVR_UNTRACKED;

	/** Decoration for tracked resource with a merge conflict.  */
	public final static ImageDescriptor OVR_CONFLICT;

	/** Decoration for tracked resources that we want to ignore changes in. */
	public final static ImageDescriptor OVR_ASSUMEVALID;

	/** Decoration for tracked resources that are dirty. */
	public final static ImageDescriptor OVR_DIRTY;

	/** Decoration for warning **/
	public final static ImageDescriptor OVR_ERROR;

	/** Find icon */
	public final static ImageDescriptor ELCL16_FIND;
	/** Compare / View icon */
	public final static ImageDescriptor ELCL16_COMPARE_VIEW;
	/** Next arrow icon */
	public final static ImageDescriptor ELCL16_NEXT;
	/** Previous arrow icon */
	public final static ImageDescriptor ELCL16_PREVIOUS;
	/** Commit icon */
	public final static ImageDescriptor ELCL16_COMMIT;
	/** Comments icon */
	public final static ImageDescriptor ELCL16_COMMENTS;
	/** Author icon */
	public final static ImageDescriptor ELCL16_AUTHOR;
	/** Committer icon */
	public final static ImageDescriptor ELCL16_COMMITTER;
	/** Id icon */
	public final static ImageDescriptor ELCL16_ID;
	/** Delete icon */
	public final static ImageDescriptor ELCL16_DELETE;
	/** Add icon */
	public final static ImageDescriptor ELCL16_ADD;
	/** Trash icon */
	public final static ImageDescriptor ELCL16_TRASH;
	/** Clear icon */
	public final static ImageDescriptor ELCL16_CLEAR;
	/** Refresh icon */
	public final static ImageDescriptor ELCL16_REFRESH;
	/** Linked with icon */
	public final static ImageDescriptor ELCL16_SYNCED;
	/** Filter icon */
	public final static ImageDescriptor ELCL16_FILTER;

	/** Enabled, checked, checkbox image */
	public final static ImageDescriptor CHECKBOX_ENABLED_CHECKED;
	/** Enabled, unchecked, checkbox image */
	public final static ImageDescriptor CHECKBOX_ENABLED_UNCHECKED;
	/** Disabled, checked, checkbox image */
	public final static ImageDescriptor CHECKBOX_DISABLED_CHECKED;
	/** Disabled, unchecked, checkbox image */
	public final static ImageDescriptor CHECKBOX_DISABLED_UNCHECKED;
	/** Edit configuration */
	public final static ImageDescriptor EDITCONFIG;
	/** Create Patch Wizard banner */
	public final static ImageDescriptor WIZBAN_CREATE_PATCH;

	/** Import Wizard banner */
	public final static ImageDescriptor WIZBAN_IMPORT_REPO;

	/** Connect Wizard banner */
	public final static ImageDescriptor WIZBAN_CONNECT_REPO;

	/** History view, select all version in same project */
	public final static ImageDescriptor FILTERPROJECT;

	/** History view, select all version in same folder */
	public final static ImageDescriptor FILTERFOLDER;

	/** History view, select all version of resource */
	public final static ImageDescriptor FILTERRESOURCE;

	/** Import button */
	public final static ImageDescriptor FETCH;

	/** Import button */
	public final static ImageDescriptor PULL;

	/** Export button */
	public final static ImageDescriptor PUSH;

	/** Collapse all button */
	public final static ImageDescriptor COLLAPSEALL;

	/** Repository tree node */
	public final static ImageDescriptor REPOSITORY;

	/** Gerrit Repository tree node */
	public final static ImageDescriptor REPOSITORY_GERRIT;

	/** New Repository button */
	public final static ImageDescriptor NEW_REPOSITORY;

	/** Create Repository button */
	public final static ImageDescriptor CREATE_REPOSITORY;

	/** Remote Repository tree node */
	public final static ImageDescriptor REMOTE_REPOSITORY;

	/** Reset */
	public final static ImageDescriptor RESET;

	/** Remote Repository tree node */
	public final static ImageDescriptor REMOTE_SPEC;

	/** Branches tree node */
	public final static ImageDescriptor BRANCHES;

	/** Checked-out decorator for branch */
	public final static ImageDescriptor OVR_CHECKEDOUT;

	/** Tags icon */
	public final static ImageDescriptor TAGS;

	/** Tag icon */
	public final static ImageDescriptor TAG;

	/** Create Tag icon */
	public final static ImageDescriptor CREATE_TAG;

	/** Branch icon */
	public final static ImageDescriptor BRANCH;

	/** Create Branch icon */
	public final static ImageDescriptor CREATE_BRANCH;

	/** Clone Icon */
	public final static ImageDescriptor CLONEGIT;

	/** Changeset Icon */
	public final static ImageDescriptor CHANGESET;

	/** Gerrit Icon */
	public final static ImageDescriptor GERRIT;

	/** Expand all icon */
	public final static ImageDescriptor EXPAND_ALL;

	/** Checkout icon */
	public final static ImageDescriptor CHECKOUT;

	/** Signed Off By icon */
	public final static ImageDescriptor SIGNED_OFF;

	/** Check all icon */
	public final static ImageDescriptor CHECK_ALL;

	/** Uncheck all icon */
	public final static ImageDescriptor UNCHECK_ALL;

	/** Amend commit icon */
	public final static ImageDescriptor AMEND_COMMIT;

	/** Untracked file icon */
	public final static ImageDescriptor UNTRACKED_FILE;

	/** Commit note icon */
	public final static ImageDescriptor NOTE;

	/** Show Annotation icon */
	public final static ImageDescriptor ANNOTATE;

	/** Commit icon */
	public final static ImageDescriptor COMMIT;

	/** Cherry-pick icon */
	public final static ImageDescriptor CHERRY_PICK;

	/** Rebase icon */
	public final static ImageDescriptor REBASE;

	/** Rebase continue icon */
	public final static ImageDescriptor REBASE_CONTINUE;

	/** Rebase skip icon */
	public final static ImageDescriptor REBASE_SKIP;

	/** Rebase abort icon */
	public final static ImageDescriptor REBASE_ABORT;

	/** Rebase process steps icon */
	public final static ImageDescriptor REBASE_PROCESS_STEPS;

	/** Merge icon */
	public final static ImageDescriptor MERGE;

	/** Annotated tag icon */
	public final static ImageDescriptor TAG_ANNOTATED;

	/** Submodules icon */
	public final static ImageDescriptor SUBMODULES;

	/** Clean icon */
	public final static ImageDescriptor CLEAN;

	/** Stash icon */
	public final static ImageDescriptor STASH;

	/** Search commit icon */
	public final static ImageDescriptor SEARCH_COMMIT;

	/** Hierarchy layout icon */
	public final static ImageDescriptor HIERARCHY;

	/** Flat presentation icon */
	public final static ImageDescriptor FLAT;

	/** Compact tree presentation icon */
	public final static ImageDescriptor COMPACT;

	/** Squash icon */
	public final static ImageDescriptor SQUASH;

	/** Fixup icon */
	public final static ImageDescriptor FIXUP;

	/** Reword icon */
	public final static ImageDescriptor REWORD;

	/** Icon for done rebase step */
	public final static ImageDescriptor DONE_STEP;

	/** Reword for current rebase step */
	public final static ImageDescriptor CURRENT_STEP;

	/** Reword for todo rebase step */
	public final static ImageDescriptor TODO_STEP;

	/** base URL */
	public final static URL base;

	static {
		base = init();
	}

	private static ImageDescriptor map(final String icon) {
		if (base != null)
			try {
				return ImageDescriptor.createFromURL(new URL(base, icon));
			} catch (MalformedURLException mux) {
				Activator.logError(UIText.UIIcons_errorLoadingPluginImage, mux);
			}
		return ImageDescriptor.getMissingImageDescriptor();
	}

	private static URL init() {
		try {
			return new URL(Activator.getDefault().getBundle().getEntry("/"), //$NON-NLS-1$
		} catch (MalformedURLException mux) {
			Activator.logError(UIText.UIIcons_errorDeterminingIconBase, mux);
			return null;
		}
	}

	/**
	 * Get the image for the given descriptor from the resource manager which
	 * handles disposal of the image when the resource manager itself is
	 * disposed.
	 *
	 * @param resourceManager
	 *            {code ResourceManager} managing the image resources
	 * @param descriptor
	 *            object describing an image
	 * @return the image for the given descriptor
	 */
	public static Image getImage(ResourceManager resourceManager,
			ImageDescriptor descriptor) {
		return (Image) resourceManager.get(descriptor);
	}
}
