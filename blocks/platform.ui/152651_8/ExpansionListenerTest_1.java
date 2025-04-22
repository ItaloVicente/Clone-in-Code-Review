package org.eclipse.ui.tests.forms.events;

import static org.junit.Assert.assertEquals;

import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.Section;
import org.junit.Before;
import org.junit.Test;

public class ExpansionListenerTest {

	private ExpansionEvent event;
	private ExpansionEventConsumer expansionEventConsumer;

	@Before
	public void setup() {
		Section section = new Section(new Shell(), SWT.NONE);
		event = new ExpansionEvent(section, true);
		expansionEventConsumer = new ExpansionEventConsumer();
	}

	@Test
	public void callsExpansionStateChangedConsumer() {
		IExpansionListener.expansionStateChangedAdapter(expansionEventConsumer).expansionStateChanged(event);
		assertEquals(event, expansionEventConsumer.calledEvent);
	}

	@Test
	public void callsExpansionStateChangingConsumer() {
		IExpansionListener.expansionStateChangingAdapter(expansionEventConsumer).expansionStateChanging(event);
		assertEquals(event, expansionEventConsumer.calledEvent);
	}

	@Test(expected = NullPointerException.class)
	public void throwsNullPointerOnNullStateChangedAdapter() {
		IExpansionListener.expansionStateChangedAdapter(null);
	}

	@Test(expected = NullPointerException.class)
	public void throwsNullPointerOnNullStateChangingAdapter() {
		IExpansionListener.expansionStateChangingAdapter(null);
	}

	class ExpansionEventConsumer implements Consumer<ExpansionEvent> {

		public ExpansionEvent calledEvent;

		@Override
		public void accept(ExpansionEvent e) {
			this.calledEvent = e;
		}
	}
}
