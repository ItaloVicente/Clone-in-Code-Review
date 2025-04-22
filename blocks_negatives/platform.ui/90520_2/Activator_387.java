/*******************************************************************************
 * Copyright (c) 2013, 2015 Dirk Fauth and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *    Dirk Fauth <dirk.fauth@googlemail.com> - initial API and implementation
 *    Fabian Miehe - Bug 440435
 *******************************************************************************/
package org.eclipse.e4.ui.internal;

import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.internal.services.ResourceBundleHelper;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.ILocaleChangeService;
import org.eclipse.e4.core.services.translation.TranslationService;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MLocalizable;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.osgi.service.log.LogService;

/**
 * Default implementation of {@link ILocaleChangeService} that changes the {@link Locale} in the
 * specified {@link IEclipseContext} and additionally fires an event on the event bus.
 *
 * @author Dirk Fauth
 *
 */
@SuppressWarnings("restriction")
public class LocaleChangeServiceImpl implements ILocaleChangeService {

	MApplication application;

	@Inject
	IEventBroker broker;

	@Inject
	@Optional
	LogService logService;

	/**
	 * Create a new {@link LocaleChangeServiceImpl} for the given {@link IEclipseContext}.
	 *
	 * @param application
	 *            The application to retrieve the context from.
	 */
	@Inject
	public LocaleChangeServiceImpl(MApplication application) {
		this.application = application;
	}

	@Override
	public void changeApplicationLocale(Locale locale) {

		this.application.getContext().set(TranslationService.LOCALE, locale);

		updateLocalization(this.application.getChildren());

		broker.post(LOCALE_CHANGE, locale);
	}

	@Override
	public void changeApplicationLocale(String localeString) {
		try {
			Locale locale = ResourceBundleHelper.toLocale(localeString);

			this.application.getContext().set(TranslationService.LOCALE, locale);

			updateLocalization(this.application.getChildren());

			broker.post(LOCALE_CHANGE, locale);
		} catch (Exception e) {
			if (logService != null)
				logService.log(LogService.LOG_ERROR, e.getMessage()
		}
	}

	/**
	 * Will iterate over the given list of {@link MUIElement}s and inform them about the Locale
	 * change if necessary.
	 *
	 * @param children
	 *            The list of {@link MUIElement}s that should be checked for Locale updates.
	 */
	@SuppressWarnings({ "rawtypes" })
	protected void updateLocalization(List<? extends MUIElement> children) {
		for (MUIElement element : children) {
			if (element instanceof MElementContainer) {
				updateLocalization(((MElementContainer) element).getChildren());
			}

			if (element instanceof MWindow) {
				MWindow window = (MWindow) element;
				MMenu mainMenu = window.getMainMenu();
				if (mainMenu != null) {
					mainMenu.updateLocalization();
					updateLocalization(mainMenu.getChildren());
				}
				updateLocalization(window.getSharedElements());
				updateLocalization(window.getWindows());
			}

			if (element instanceof MTrimmedWindow) {
				for (MTrimBar trimBar : ((MTrimmedWindow) element).getTrimBars()) {
					trimBar.updateLocalization();
					updateLocalization(trimBar.getChildren());
				}
			}

			if (element instanceof MPerspective) {
				updateLocalization(((MPerspective) element).getWindows());
			}

			if (element instanceof MPart) {
				MPart mPart = (MPart) element;
				MToolBar toolbar = mPart.getToolbar();
				if (toolbar != null && toolbar.getChildren() != null) {
					toolbar.updateLocalization();
					updateLocalization(toolbar.getChildren());
				}
			}

			((MLocalizable) element).updateLocalization();
		}
	}

}
