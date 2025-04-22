package org.eclipse.ui.tests.forms.events;

import static org.junit.Assert.assertEquals;

import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.junit.Before;
import org.junit.Test;

public class HyperLinkListenerTest {

	private HyperlinkEvent event;
	private HyperlinkEventConsumer linkEventConsumer;

	@Before
	public void setup() {
		Link link = new Link(new Shell(), SWT.NONE);
		event = new HyperlinkEvent(link, "uri://test", "link", 0);
		linkEventConsumer = new HyperlinkEventConsumer();
	}

	@Test
	public void callsActivatedConsumer() {
		IHyperlinkListener.linkActivatedAdapter(linkEventConsumer).linkActivated(event);
		assertEquals(event, linkEventConsumer.calledEvent);
	}

	@Test
	public void callsExitedConsumer() {
		IHyperlinkListener.linkExitedAdapter(linkEventConsumer).linkExited(event);
		assertEquals(event, linkEventConsumer.calledEvent);
	}

	@Test
	public void callsEnteredConsumer() {
		IHyperlinkListener.linkEnteredAdapter(linkEventConsumer).linkEntered(event);
		assertEquals(event, linkEventConsumer.calledEvent);
	}

	@Test(expected = NullPointerException.class)
	public void throwsNullPointerOnNullActivatedAdapter() {
		IHyperlinkListener.linkActivatedAdapter(null);
	}

	@Test(expected = NullPointerException.class)
	public void throwsNullPointerOnNullExitedAdapter() {
		IHyperlinkListener.linkExitedAdapter(null);
	}

	@Test(expected = NullPointerException.class)
	public void throwsNullPointerOnNullEnteredAdapter() {
		IHyperlinkListener.linkEnteredAdapter(null);
	}

	class HyperlinkEventConsumer implements Consumer<HyperlinkEvent> {

		public HyperlinkEvent calledEvent;

		@Override
		public void accept(HyperlinkEvent e) {
			this.calledEvent = e;
		}
	}
}
