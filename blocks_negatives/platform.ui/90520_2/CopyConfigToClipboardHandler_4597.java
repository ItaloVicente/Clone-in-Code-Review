/*******************************************************************************
 * Copyright (c) 2008, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Patrik Suzzi <psuzzi@gmail.com> - Bug 496319
 ******************************************************************************/

package org.eclipse.ui.internal.ide.commands;

import java.util.Properties;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.internal.ProductProperties;

/**
 * Copies the main build information to the clipboard, including os version and
 * windowing system. Useful for debugging and bug reporting/verification.
 *
 * @since 3.4
 *
 */
public class CopyBuildIdToClipboardHandler extends AbstractHandler {

	/** Platform O.S. */
	/** O.S. Version */
	/** Platform architecture property name */
	/** Platform windowing system */
	/** GTK version */
	/** WebKitGTK version */

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		final IProduct product = Platform.getProduct();
		if (product == null )

		String aboutText = ProductProperties.getAboutText(product);
		if (lines.length<=3){
		}

		Properties sp = System.getProperties();
		String osInfo = String.format("OS: %s, v.%s, %s / %s", //$NON-NLS-1$
				sp.get(OS_NAME), sp.get(OS_VERSION), sp.get(OSGI_ARCH), sp.get(OSGI_WS));

		String gtkVer = sp.getProperty(SWT_GTK_VERSION);
		if (gtkVer != null) {
			osInfo += String.format(" %s", gtkVer); //$NON-NLS-1$
		}
		String webkitGtkVer = sp.getProperty(SWT_WEBKITGTK_VERSION);
		if (webkitGtkVer != null) {
			osInfo += String.format(", WebKit %s", webkitGtkVer); //$NON-NLS-1$
		}

		String toCopy = String.format("%s%n%s%n%s%n%s%n", lines[0], lines[2], lines[3], osInfo); //$NON-NLS-1$

		Clipboard clipboard = new Clipboard(null);
		try {
			TextTransfer textTransfer = TextTransfer.getInstance();
			Transfer[] transfers = new Transfer[] { textTransfer };
			Object[] data = new Object[] { toCopy };
			clipboard.setContents(data, transfers);
		} finally {
			clipboard.dispose();
		}
		return null;
	}
}
