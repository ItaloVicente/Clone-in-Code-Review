package org.eclipse.jface.tests.widgets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.jface.widgets.LinkFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.junit.Test;

public class TestUnitLinkFactory extends AbstractFactoryTest {

	@Test
	public void createsLink() {
		Link link = LinkFactory.newLink(SWT.NONE).create(shell);

		assertEquals(shell, link.getParent());
		assertEquals(SWT.NONE, link.getStyle() & SWT.NONE);
	}

	@Test
	public void createLinksWithAllProperties() {
		final SelectionEvent[] raisedEvents = new SelectionEvent[1];
		Link link = LinkFactory.newLink(SWT.NONE).text("Test Link").onSelect(e -> raisedEvents[0] = e).create(shell);

		link.notifyListeners(SWT.Selection, new Event());

		assertEquals("Test Link", link.getText());
		assertEquals("test", link.getData());

		assertEquals(1, link.getListeners(SWT.Selection).length);
		assertNotNull(raisedEvents[0]);
	}
}
