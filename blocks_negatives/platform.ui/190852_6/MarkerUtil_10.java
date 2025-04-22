/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.views.bookmarkexplorer;

import org.eclipse.ui.PlatformUI;

/**
 * Help context ids for the bookmark view.
 * <p>
 * This interface contains constants only; it is not intended to be implemented
 * or extended.
 * </p>
 *
 * Marked for deletion, see Bug 550439
 *
 * @noreference
 * @noinstantiate This class is not intended to be instantiated by clients.
 * @noextend This class is not intended to be subclassed by clients.
 */
@Deprecated
interface IBookmarkHelpContextIds {

	String COPY_BOOKMARK_ACTION = PREFIX

	String PASTE_BOOKMARK_ACTION = PREFIX

	String REMOVE_BOOKMARK_ACTION = PREFIX

	String OPEN_BOOKMARK_ACTION = PREFIX

	String SELECT_ALL_BOOKMARK_ACTION = PREFIX

	String BOOKMARK_PROPERTIES_ACTION = PREFIX

	String SORT_ASCENDING_ACTION = PREFIX

	String SORT_DESCENDING_ACTION = PREFIX

	String SORT_DESCRIPTION_ACTION = PREFIX

	String SORT_RESOURCE_ACTION = PREFIX

	String SORT_FOLDER_ACTION = PREFIX

	String SORT_LOCATION_ACTION = PREFIX

	String SORT_CREATION_TIME_ACTION = PREFIX

}
