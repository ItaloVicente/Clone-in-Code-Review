package org.eclipse.e4.ui.swt.internal.cocoa;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.css.swt.theme.ITheme;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.swt.internal.cocoa.OS;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.event.EventHandler;

@SuppressWarnings("restriction")
public class CocoaDarkThemeProcessor {

	@Inject
	IEventBroker eventBroker;

	private EventHandler eventHandler;

	@PostConstruct
	public void intialize() {

		eventHandler = event -> {
			if (event == null) {
				return;
			}
			ITheme theme = (ITheme) event.getProperty("theme"); //$NON-NLS-1$
			final boolean isDark = theme.getId().contains("dark"); //$NON-NLS-1$
			Display display = (Display) event.getProperty(IThemeEngine.Events.DEVICE);

			display.asyncExec(() -> OS.setTheme(isDark));
		};
		eventBroker.subscribe(IThemeEngine.Events.THEME_CHANGED, eventHandler);
	}

	@PreDestroy
	public void cleanUp() {
		eventBroker.unsubscribe(eventHandler);
	}

}
