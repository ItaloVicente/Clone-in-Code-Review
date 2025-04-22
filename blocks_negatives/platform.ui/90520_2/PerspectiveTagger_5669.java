/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.ui.internal;

import org.eclipse.e4.ui.workbench.modeling.EModelService;

import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;

public class PerspectiveTagger {
	/**
	 * Alters known 3.x perspective part folders into their e4 counterparts.
	 */
	public static void tagPerspective(MPerspective perspective, EModelService modelService) {
		String id = perspective.getElementId();
		if (id == null) {
			return;
		}

			tagJavaPerspective(perspective, modelService);
			tagCVSPerspective(perspective, modelService);
			tagTeamPerspective(perspective, modelService);
			tagDebugPerspective(perspective, modelService);
			tagResourcePerspective(perspective, modelService);
			tagPluginDevelopmentPerspective(perspective, modelService);
		}
	}

	static void tagJavaPerspective(MPerspective perspective, EModelService modelService) {
		MUIElement element = modelService.find("left", perspective); //$NON-NLS-1$
		if (element != null) {
		}

		element = modelService.find("bottom", perspective); //$NON-NLS-1$
		if (element != null) {
		}

		element = modelService.find("right", perspective); //$NON-NLS-1$
		if (element != null) {
		}
	}

	static void tagCVSPerspective(MPerspective perspective, EModelService modelService) {
		MUIElement element = modelService.find("top", perspective); //$NON-NLS-1$
		if (element != null) {
		}
	}

	static void tagTeamPerspective(MPerspective perspective, EModelService modelService) {
		MUIElement element = modelService.find("top", perspective); //$NON-NLS-1$
		if (element != null) {
		}

		element = modelService.find("top2", perspective); //$NON-NLS-1$
		if (element != null) {
		}
	}

	static void tagDebugPerspective(MPerspective perspective, EModelService modelService) {
		MUIElement element = modelService.find(
				"org.eclipse.debug.internal.ui.NavigatorFolderView", perspective); //$NON-NLS-1$
		if (element != null) {
		}

		element = modelService.find(
				"org.eclipse.debug.internal.ui.ConsoleFolderView", perspective); //$NON-NLS-1$
		if (element != null) {
		}

		element = modelService.find(
				"org.eclipse.debug.internal.ui.OutlineFolderView", perspective); //$NON-NLS-1$
		if (element != null) {
		}
	}

	static void tagResourcePerspective(MPerspective perspective, EModelService modelService) {
		MUIElement element = modelService.find("topLeft", perspective); //$NON-NLS-1$
		if (element != null) {
		}

		element = modelService.find("bottomRight", perspective); //$NON-NLS-1$
		if (element != null) {
		}

		element = modelService.find("bottomLeft", perspective); //$NON-NLS-1$
		if (element != null) {
		}
	}

	static void tagPluginDevelopmentPerspective(MPerspective perspective,
			EModelService modelService) {
		MUIElement element = modelService.find("topLeft", perspective); //$NON-NLS-1$
		if (element != null) {
		}

		element = modelService.find("bottomRight", perspective); //$NON-NLS-1$
		if (element != null) {
		}
	}
}
