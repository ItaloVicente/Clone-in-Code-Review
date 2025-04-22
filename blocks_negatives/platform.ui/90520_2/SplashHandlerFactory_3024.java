/*******************************************************************************
 * Copyright (c) 2007, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Lars Vogel <Lars.Vogel@vogella.com> - Bug 430848
 *******************************************************************************/
package org.eclipse.ui.internal.splash;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.css.swt.CSSSWTConstants;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.branding.IProductConstants;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.splash.BasicSplashHandler;

/**
 * Parses the well known product constants and constructs a splash handler
 * accordingly.
 */
public class EclipseSplashHandler extends BasicSplashHandler {


	/**
	 * Initializes the splash screen.
	 * <p>
	 * <strong>WARNING:</strong> Do not change the default values as existing
	 * products might rely on them.
	 * </p>
	 *
	 * @param splash
	 *            the shell that contains the splash screen
	 */
	@Override
	public void init(Shell splash) {
		super.init(splash);
		String progressRectString = null;
		String messageRectString = null;
		String foregroundColorString = null;
		IProduct product = Platform.getProduct();
		if (product != null) {
			progressRectString = product
					.getProperty(IProductConstants.STARTUP_PROGRESS_RECT);
			messageRectString = product
					.getProperty(IProductConstants.STARTUP_MESSAGE_RECT);
			foregroundColorString = product
					.getProperty(IProductConstants.STARTUP_FOREGROUND_COLOR);
		}
		Rectangle progressRect = StringConverter.asRectangle(
				progressRectString, new Rectangle(10, 10, 300, 15));
		setProgressRect(progressRect);

		Rectangle messageRect = StringConverter.asRectangle(messageRectString,
				new Rectangle(10, 35, 300, 15));
		setMessageRect(messageRect);

		int foregroundColorInteger;
		try {
			foregroundColorInteger = Integer
					.parseInt(foregroundColorString, 16);
		} catch (Exception ex) {
		}

		setForeground(new RGB((foregroundColorInteger & 0xFF0000) >> 16,
				(foregroundColorInteger & 0xFF00) >> 8,
				foregroundColorInteger & 0xFF));
		if (PrefUtil.getInternalPreferenceStore().getBoolean(
			final String buildId = System.getProperty(
					"eclipse.buildId", "Unknown Build"); //$NON-NLS-1$ //$NON-NLS-2$

			if (buildIdLocString != null) {
				if (buildIdSize != null) {
					buildIdLocString += ", + buildIdSize; //$NON-NLS-1$
-				} else {
-					buildIdLocString += ,100,40"; //$NON-NLS-1$
				}
			}
			Rectangle buildIdRectangle = StringConverter.asRectangle(buildIdLocString,
					new Rectangle(322, 190, 100, 40));

			Label idLabel = new Label(getContent(), SWT.RIGHT);
			idLabel.setForeground(getForeground());
			idLabel.setBounds(buildIdRectangle);
			idLabel.setText(buildId);
			idLabel.setData(CSSSWTConstants.CSS_ID_KEY, CSS_ID_SPLASH_BUILD_ID);
		}
		else {
		}
	}
}
