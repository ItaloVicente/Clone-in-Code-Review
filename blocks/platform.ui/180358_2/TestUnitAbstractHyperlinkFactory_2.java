package org.eclipse.jface.tests.widgets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.jface.widgets.HyperlinkFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.junit.Test;

public class TestUnitAbstractHyperlinkFactory extends AbstractFactoryTest {

	@Test
	public void createsHyperlink() {
		final HyperlinkEvent[] raisedEvents = new HyperlinkEvent[1];
		Object hrefObject = new Object();

		Hyperlink hyperlink = HyperlinkFactory.newHyperlink(SWT.NONE).href(hrefObject).onClick(e -> raisedEvents[0] = e)
				.create(shell);

		Event event = new Event();
		event.character = '\r';
		hyperlink.notifyListeners(SWT.KeyDown, event);

		assertEquals(hrefObject, hyperlink.getHref());
		assertNotNull(raisedEvents[0]);
	}
}
