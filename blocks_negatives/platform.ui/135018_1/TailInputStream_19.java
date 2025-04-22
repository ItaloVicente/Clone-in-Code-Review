/*******************************************************************************
 * Copyright (c) 2007, 2016 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Jacek Pospychala <jacek.pospychala@pl.ibm.com> - bug 202583
 *******************************************************************************/
package org.eclipse.ui.internal.views.log;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public final class SharedImages {

	}



	/* Event Details */



	public static ImageDescriptor getImageDescriptor(String key) {
		return Activator.getDefault().getImageRegistry().getDescriptor(key);
	}

	public static Image getImage(String key) {
		return Activator.getDefault().getImageRegistry().get(key);
	}

}
