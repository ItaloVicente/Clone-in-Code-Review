package org.eclipse.ui.examples.urischemehandler.handlers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

public class OpenUrlEventCreationHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent e) throws ExecutionException {
		
		Event event = new Event();
		event.text = "hello://demo.url";
		event.type = SWT.OpenUrl;

		invokeSendEvent(event);
		
		return null;
	}

	private void invokeSendEvent(Event event) {
		Display display = Display.getDefault();
		try {
			Method sendEventMethod = display.getClass().getDeclaredMethod("sendEvent", int.class, Event.class);
			sendEventMethod.setAccessible(true);
			sendEventMethod.invoke(display, event.type, event);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
	}
}
