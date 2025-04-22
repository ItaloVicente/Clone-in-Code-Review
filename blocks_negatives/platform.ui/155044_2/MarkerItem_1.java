/*******************************************************************************
 *  Copyright (c) 2019 Eclipse Foundation and others.
 *
 *  This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *     Eclipse Foundation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.views.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.views.markers.MarkerItem;

/**
 * Defaults contract for {@linkplain MarkerItem}.
 *
 * @since 3.16
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface MarkerItemDefaults {

	/**
	 * {@linkplain MarkerItem} {@code location} default value constant.
	 *
	 * @see MarkerItem#getAttributeValue(String, String)
	 * @see IMarker#LOCATION
	 * @see MarkerItem#getLocation()
	 * @since 3.16
	 */

	/**
	 * {@linkplain MarkerItem} {@code path} default value constant.
	 *
	 * @see MarkerItem#getPath()
	 * @since 3.16
	 */

}
