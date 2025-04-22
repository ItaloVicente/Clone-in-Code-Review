/*******************************************************************************
 * Copyright (c) 2010, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.e4.ui.progress.internal.legacy;

public class PlatformUI {

	public static boolean isWorkbenchRunning() {
	    return true;
    }

	public static boolean isWorkbenchStarting() {
	    return false;
    }

}
