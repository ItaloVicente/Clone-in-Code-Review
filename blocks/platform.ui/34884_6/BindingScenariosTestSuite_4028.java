package org.eclipse.jface.tests.databinding.scenarios;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;


public class AutomationUtil {

	public static void performKeyCodeEvent(Display display, int eventType,
			int keyCode) {
		Event event = new Event();
		event.type = eventType;
		event.keyCode = keyCode;
		display.post(event);
	}

	public static void performCharacterEvent(Display display, int eventType,
			char character) {
		Event event = new Event();
		event.type = eventType;
		event.character = character;
		display.post(event);
	}
}
