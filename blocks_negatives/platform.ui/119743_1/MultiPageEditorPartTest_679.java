/*******************************************************************************
 * Copyright (c) 2019 Tim Neumann <tim.neumann@advantest.com> and others
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Tim Neumann <tim.neumann@advantest.com> - initial API and implementation
 ******************************************************************************/
package org.eclipse.ui.tests.markers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerHelpContextProvider;

/**
 * Test MarkerHelpContextProvider for
 * {@link MarkerHelpRegistryTest#testGetHelpForHelpContextProvider()}
 */
public class TestMarkerHelpContextProvider implements IMarkerHelpContextProvider {

	private static List<IMarker> paramsHasHc;
	private static List<IMarker> paramsGetHc;

	public static void init() {
		paramsHasHc = new ArrayList<>();
		paramsGetHc = new ArrayList<>();
	}

	@Override
	public String getHelpContextForMarker(IMarker marker) {
		paramsGetHc.add(marker);
		return marker.getAttribute(MarkerHelpRegistryTest.ATT_HELP_CONTEXT, null);
	}

	@Override
	public boolean hasHelpContextForMarker(IMarker marker) {
		paramsHasHc.add(marker);
		return marker.getAttribute(MarkerHelpRegistryTest.ATT_HAS_HELP, false);
	}

	/**
	 * @return Returns the paramsHasHc.
	 */
	public static List<IMarker> getParamsHasHc() {
		return paramsHasHc;
	}

	/**
	 * @return Returns the paramsGetHc.
	 */
	public static List<IMarker> getParamsGetHc() {
		return paramsGetHc;
	}
}
