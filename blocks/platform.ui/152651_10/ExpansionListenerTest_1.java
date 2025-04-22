package org.eclipse.ui.tests.forms.events;

import static org.junit.Assert.assertEquals;

import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.Section;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExpansionListenerTest {

	private ExpansionEvent event;
	private ExpansionEventConsumer expansionEventConsumer;
	private static Shell shell;
	private static Section section;

	@BeforeClass
	public static void classSetup() {
		shell = new Shell();
		section = new Section(shell, SWT.NONE);
	}

	@Before
	public void setup() {
		event = new ExpansionEvent(section, true);
		expansionEventConsumer = new ExpansionEventConsumer();
	}

	@AfterClass
	public static void classTeardown() {
		shell.dispose();
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
