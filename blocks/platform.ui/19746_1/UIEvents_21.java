package org.eclipse.e4.ui.internal;

import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.internal.services.ResourceBundleHelper;
import org.eclipse.e4.core.internal.services.ServicesActivator;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.ILocaleChangeService;
import org.eclipse.e4.core.services.translation.TranslationService;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MLocalizable;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.osgi.service.log.LogService;

@SuppressWarnings("restriction")
public class LocaleChangeServiceImpl implements ILocaleChangeService {

	private static LogService logService = ServicesActivator.getDefault().getLogService();

	MApplication application;

	@Inject
	IEventBroker broker;

	@Inject
	public LocaleChangeServiceImpl(MApplication application) {
		this.application = application;
	}

	public void changeApplicationLocale(Locale locale) {

		this.application.getContext().set(TranslationService.LOCALE, locale.toString());

		updateLocalization(this.application.getChildren());

		broker.post(LOCALE_CHANGE, locale);
	}

	public void changeContextLocale(IEclipseContext context, Locale locale) {
		context.set(TranslationService.LOCALE, locale.toString());

		updateLocalization(this.application.getChildren());

		broker.post(LOCALE_CHANGE, locale);
	}

	public void changeApplicationLocale(String localeString) {
		try {
			Locale locale = ResourceBundleHelper.toLocale(localeString);

			this.application.getContext().set(TranslationService.LOCALE, localeString);

			updateLocalization(this.application.getChildren());

			broker.post(LOCALE_CHANGE, locale);
		} catch (IllegalArgumentException e) {
			if (logService != null)
				logService.log(LogService.LOG_ERROR, e.getMessage()
						+ " - No Locale change will be performed."); //$NON-NLS-1$
		}
	}

	public void changeContextLocale(IEclipseContext context, String localeString) {
		try {
			Locale locale = ResourceBundleHelper.toLocale(localeString);

			context.set(TranslationService.LOCALE, localeString);

			updateLocalization(this.application.getChildren());

			broker.post(LOCALE_CHANGE, locale);
		} catch (IllegalArgumentException e) {
			if (logService != null)
				logService.log(LogService.LOG_ERROR, e.getMessage()
						+ " - No Locale change will be performed."); //$NON-NLS-1$
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void updateLocalization(List<? extends MUIElement> children) {
		for (MUIElement element : children) {
			if (element instanceof MElementContainer) {
				updateLocalization(((MElementContainer) element).getChildren());
			}

			if (element instanceof MWindow) {
				((MWindow) element).getMainMenu().updateLocalization();
				updateLocalization(((MWindow) element).getMainMenu().getChildren());
			}

			if (element instanceof MTrimmedWindow) {
				for (MTrimBar trimBar : ((MTrimmedWindow) element).getTrimBars()) {
					trimBar.updateLocalization();
					updateLocalization(trimBar.getChildren());
				}
			}

			((MLocalizable) element).updateLocalization();
		}
	}

}
