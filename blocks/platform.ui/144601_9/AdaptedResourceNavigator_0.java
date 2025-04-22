package org.eclipse.jface.tests.fieldassist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

public abstract class FieldAssistTestCase extends AbstractFieldAssistTestCase {
	static final String SAMPLE_CONTENT = "s";
	static final char ACTIVATE_CHAR = 'i';
	static final char EXTRA_CHAR = 'b';

	@Test
	public void testAutoactivateNoDelay() {
		AbstractFieldAssistWindow window = getFieldAssistWindow();
		window.setPropagateKeys(false);
		window.setAutoActivationDelay(0);
		window.setAutoActivationCharacters(new char [] {ACTIVATE_CHAR});
		window.open();
		setControlContent(SAMPLE_CONTENT);
		sendKeyDownToControl(ACTIVATE_CHAR);
		ensurePopupIsUp();
		assertTwoShellsUp();
	}

	@Test
	public void testAutoactivateWithDelay() {
		AbstractFieldAssistWindow window = getFieldAssistWindow();
		window.setPropagateKeys(false);
		window.setAutoActivationDelay(600);
		window.setAutoActivationCharacters(new char [] {ACTIVATE_CHAR});
		window.open();
		setControlContent(SAMPLE_CONTENT);
		sendKeyDownToControl(ACTIVATE_CHAR);
		ensurePopupIsUp();
		assertTwoShellsUp();
	}

	@Test
	public void testExplicitActivate() {
		AbstractFieldAssistWindow window = getFieldAssistWindow();
		window.setPropagateKeys(false);
		KeyStroke stroke = KeyStroke.getInstance(SWT.F4);
		window.setKeyStroke(stroke);
		window.open();
		sendKeyDownToControl(stroke);
		assertTwoShellsUp();
	}

	@Test
	public void testPopupDeactivates() {
		AbstractFieldAssistWindow window = getFieldAssistWindow();
		window.setPropagateKeys(false);
		window.setAutoActivationDelay(0);
		window.setAutoActivationCharacters(new char [] {ACTIVATE_CHAR});
		window.open();
		setControlContent(SAMPLE_CONTENT);
		sendKeyDownToControl(ACTIVATE_CHAR);
		ensurePopupIsUp();
		assertTwoShellsUp();
		sendFocusElsewhere();
		spinEventLoop();
		assertOneShellUp();
	}

	@Test
	public void testPropagateKeysOff() {
		AbstractFieldAssistWindow window = getFieldAssistWindow();
		window.setPropagateKeys(false);
		window.setAutoActivationCharacters(new char [] {ACTIVATE_CHAR});
		window.open();
		setControlContent(SAMPLE_CONTENT);
		sendKeyDownToControl(ACTIVATE_CHAR);
		ensurePopupIsUp();
		assertTwoShellsUp();
		sendKeyDownToControl(EXTRA_CHAR);
		assertEquals("1.0", SAMPLE_CONTENT + new String(new char [] {ACTIVATE_CHAR}), getControlContent());
	}

	@Test
	public void testPropagateKeysOn() {
		AbstractFieldAssistWindow window = getFieldAssistWindow();
		window.setPropagateKeys(true);
		window.setAutoActivationCharacters(new char [] {ACTIVATE_CHAR});
		window.open();
		setControlContent(SAMPLE_CONTENT);
		sendKeyDownToControl(ACTIVATE_CHAR);
		ensurePopupIsUp();
		assertTwoShellsUp();
		sendKeyDownToControl(EXTRA_CHAR);
		assertEquals("1.0", SAMPLE_CONTENT + new String(new char [] {ACTIVATE_CHAR, EXTRA_CHAR}), getControlContent());
	}

	@Test
	public void testBug262022() {
		AbstractFieldAssistWindow window = getFieldAssistWindow();
		window.setPropagateKeys(false);
		window.setAutoActivationCharacters(new char [] {ACTIVATE_CHAR});
		window.open();
		setControlContent(SAMPLE_CONTENT);
		sendKeyDownToControl(ACTIVATE_CHAR);
		ensurePopupIsUp();
		assertTwoShellsUp();
		Event event = new Event();
		event.type = SWT.KeyDown;
		event.keyCode = SWT.ARROW_LEFT;
		window.getDisplay().post(event);
		window.getDisplay().asyncExec(this::closeFieldAssistWindow);
		spinEventLoop();
	}

	@Test
	public void testBug279953() {
		AbstractFieldAssistWindow window = getFieldAssistWindow();
		window.setPropagateKeys(false);
		window.setAutoActivationCharacters(new char [] {ACTIVATE_CHAR});
		window.open();
		assertOneShellUp();
		ControlDecoration decoration = new ControlDecoration(getFieldAssistWindow().getFieldAssistControl(), SWT.RIGHT);
		decoration.setImage(FieldDecorationRegistry.getDefault()
			.getFieldDecoration(FieldDecorationRegistry.DEC_INFORMATION).getImage());
		decoration.setDescriptionText("");
		decoration.showHoverText("");
		assertOneShellUp();
	}

	@Test
	public void testDecorationIsVisible() {
		AbstractFieldAssistWindow window = getFieldAssistWindow();
		window.setPropagateKeys(false);
		window.setAutoActivationCharacters(new char [] {ACTIVATE_CHAR});
		window.open();
		assertOneShellUp();
		ControlDecoration decoration = new ControlDecoration(getFieldAssistWindow().getFieldAssistControl(), SWT.RIGHT);
		decoration.setImage(FieldDecorationRegistry.getDefault()
			.getFieldDecoration(FieldDecorationRegistry.DEC_INFORMATION).getImage());
		decoration.setDescriptionText("foo");
		spinEventLoop();
		assertTrue("1.0", decoration.isVisible());
		decoration.hide();
		assertFalse("1.1", decoration.isVisible());
		decoration.setShowOnlyOnFocus(true);
		sendFocusElsewhere();
		sendFocusInToControl();
		spinEventLoop();
		assertFalse("1.2", decoration.isVisible());
		decoration.show();
		assertTrue("1.3", decoration.isVisible());
		sendFocusElsewhere();
		spinEventLoop();
		assertFalse("1.4", decoration.isVisible());
		decoration.setShowOnlyOnFocus(false);
		assertTrue("1.5", decoration.isVisible());
		window.getFieldAssistControl().setVisible(false);
		assertFalse("1.6", decoration.isVisible());
		decoration.hide();
		window.getFieldAssistControl().setVisible(true);
		assertFalse("1.7", decoration.isVisible());
		decoration.show();
		assertTrue("1.8", decoration.isVisible());
	}

	@Test
	public void testPopupFocus() {
		AbstractFieldAssistWindow window = getFieldAssistWindow();
		window.setPropagateKeys(false);
		KeyStroke stroke = KeyStroke.getInstance(SWT.F4);
		window.setKeyStroke(stroke);
		window.open();
		sendKeyDownToControl(stroke);
		assertTwoShellsUp();

		window.getFieldAssistControl().setFocus();
		spinEventLoop();
		assertFalse("1.0", window.getContentProposalAdapter().hasProposalPopupFocus());
		window.getContentProposalAdapter().setProposalPopupFocus();
		spinEventLoop();
		assertTrue("1.1", window.getContentProposalAdapter().hasProposalPopupFocus());

		sendFocusElsewhere();
		spinEventLoop();
		assertOneShellUp();
		assertFalse("1.2", window.getContentProposalAdapter().hasProposalPopupFocus());
	}

	@Test
	public void testPopupIsOpen() {
		AbstractFieldAssistWindow window = getFieldAssistWindow();
		window.setPropagateKeys(false);
		KeyStroke stroke = KeyStroke.getInstance(SWT.F4);
		window.setKeyStroke(stroke);
		window.open();

		assertFalse("1.0", window.getContentProposalAdapter().isProposalPopupOpen());
		sendKeyDownToControl(stroke);
		assertTwoShellsUp();
		assertTrue("1.1", window.getContentProposalAdapter().isProposalPopupOpen());

		sendFocusElsewhere();
		spinEventLoop();
		assertOneShellUp();
		assertFalse("1.2", window.getContentProposalAdapter().isProposalPopupOpen());
	}

	@Test
	public void testBug256651ReplaceMode() {
		AbstractFieldAssistWindow window = getFieldAssistWindow();
		window.setPropagateKeys(false);
		window.setAutoActivationCharacters(new char [] {ACTIVATE_CHAR});
		window.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
		window.open();
		Display display = getDisplay();
		Rectangle displayBounds = display.getBounds();
		window.getShell().setLocation(0, displayBounds.height - window.getShell().getBounds().height);
		assertOneShellUp();
		setControlContent(SAMPLE_CONTENT);
		sendKeyDownToControl(ACTIVATE_CHAR);
		ensurePopupIsUp();
		assertTwoShellsUp();
		sendFocusToPopup();
		Shell popupShell = display.getActiveShell();
		Rectangle popupBounds = popupShell.getBounds();
		Rectangle controlBounds = getFieldAssistWindow().getFieldAssistControl().getBounds();
		controlBounds = getDisplay().map(getFieldAssistWindow().getFieldAssistControl().getParent(), null, controlBounds);
		assertFalse("Popup is blocking the control", popupBounds.intersects(controlBounds));
	}

	@Test
	public void testDefaultPopupPositioningReplaceMode() {
		AbstractFieldAssistWindow window = getFieldAssistWindow();
		window.setPropagateKeys(false);
		window.setAutoActivationCharacters(new char [] {ACTIVATE_CHAR});
		window.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
		window.open();
		Display display = getDisplay();
		window.getShell().setLocation(0, 0);
		assertOneShellUp();
		setControlContent(SAMPLE_CONTENT);
		sendKeyDownToControl(ACTIVATE_CHAR);
		ensurePopupIsUp();
		assertTwoShellsUp();
		sendFocusToPopup();
		Shell popupShell = display.getActiveShell();
		Rectangle popupBounds = popupShell.getBounds();
		Rectangle controlBounds = getFieldAssistWindow().getFieldAssistControl().getBounds();
		controlBounds = getDisplay().map(getFieldAssistWindow().getFieldAssistControl().getParent(), null, controlBounds);
		assertFalse("Popup is blocking the control", popupBounds.intersects(controlBounds));
	}
}
