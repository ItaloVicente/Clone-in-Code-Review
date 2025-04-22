package org.eclipse.egit.ui.internal.dialogs;

import org.eclipse.core.runtime.Assert;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IPainter;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITextViewerExtension2;
import org.eclipse.jface.text.WhitespaceCharacterPainter;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.texteditor.AbstractTextEditor;

public class ShowWhitespaceAction extends TextEditorPropertyAction {

	public ShowWhitespaceAction(ITextViewer viewer) {
		this(viewer, false);
	}

	public ShowWhitespaceAction(ITextViewer viewer, boolean initiallyOff) {
		super(UIText.SpellcheckableMessageArea_showWhitespace, viewer,
				AbstractTextEditor.PREFERENCE_SHOW_WHITESPACE_CHARACTERS,
				initiallyOff);
	}

	private IPainter whitespaceCharPainter;

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String property = event.getProperty();
		if (property == null) {
			return;
		}
		switch (property) {
		case AbstractTextEditor.PREFERENCE_SHOW_WHITESPACE_CHARACTERS:
		case AbstractTextEditor.PREFERENCE_SHOW_LEADING_SPACES:
		case AbstractTextEditor.PREFERENCE_SHOW_ENCLOSED_SPACES:
		case AbstractTextEditor.PREFERENCE_SHOW_TRAILING_SPACES:
		case AbstractTextEditor.PREFERENCE_SHOW_LEADING_IDEOGRAPHIC_SPACES:
		case AbstractTextEditor.PREFERENCE_SHOW_ENCLOSED_IDEOGRAPHIC_SPACES:
		case AbstractTextEditor.PREFERENCE_SHOW_TRAILING_IDEOGRAPHIC_SPACES:
		case AbstractTextEditor.PREFERENCE_SHOW_LEADING_TABS:
		case AbstractTextEditor.PREFERENCE_SHOW_ENCLOSED_TABS:
		case AbstractTextEditor.PREFERENCE_SHOW_TRAILING_TABS:
		case AbstractTextEditor.PREFERENCE_SHOW_CARRIAGE_RETURN:
		case AbstractTextEditor.PREFERENCE_SHOW_LINE_FEED:
		case AbstractTextEditor.PREFERENCE_WHITESPACE_CHARACTER_ALPHA_VALUE:
			synchronizeWithPreference();
			break;
		default:
			break;
		}
	}

	@Override
	protected final String getPreferenceKey() {
		return AbstractTextEditor.PREFERENCE_SHOW_WHITESPACE_CHARACTERS;
	}

	@Override
	protected void toggleState(boolean checked) {
		if (checked) {
			installPainter();
		} else {
			uninstallPainter();
		}
	}

	private void installPainter() {
		Assert.isTrue(whitespaceCharPainter == null);
		ITextViewer v = getTextViewer();
		if (v instanceof ITextViewerExtension2) {
			IPreferenceStore store = getStore();
			whitespaceCharPainter = new WhitespaceCharacterPainter(v,
					store.getBoolean(
							AbstractTextEditor.PREFERENCE_SHOW_LEADING_SPACES),
					store.getBoolean(
							AbstractTextEditor.PREFERENCE_SHOW_ENCLOSED_SPACES),
					store.getBoolean(
							AbstractTextEditor.PREFERENCE_SHOW_TRAILING_SPACES),
					store.getBoolean(
							AbstractTextEditor.PREFERENCE_SHOW_LEADING_IDEOGRAPHIC_SPACES),
					store.getBoolean(
							AbstractTextEditor.PREFERENCE_SHOW_ENCLOSED_IDEOGRAPHIC_SPACES),
					store.getBoolean(
							AbstractTextEditor.PREFERENCE_SHOW_TRAILING_IDEOGRAPHIC_SPACES),
					store.getBoolean(
							AbstractTextEditor.PREFERENCE_SHOW_LEADING_TABS),
					store.getBoolean(
							AbstractTextEditor.PREFERENCE_SHOW_ENCLOSED_TABS),
					store.getBoolean(
							AbstractTextEditor.PREFERENCE_SHOW_TRAILING_TABS),
					store.getBoolean(
							AbstractTextEditor.PREFERENCE_SHOW_CARRIAGE_RETURN),
					store.getBoolean(
							AbstractTextEditor.PREFERENCE_SHOW_LINE_FEED),
					store.getInt(
							AbstractTextEditor.PREFERENCE_WHITESPACE_CHARACTER_ALPHA_VALUE));
			((ITextViewerExtension2) v).addPainter(whitespaceCharPainter);
		}
	}

	private void uninstallPainter() {
		if (whitespaceCharPainter == null) {
			return;
		}
		ITextViewer v = getTextViewer();
		if (v instanceof ITextViewerExtension2) {
			((ITextViewerExtension2) v).removePainter(whitespaceCharPainter);
		}
		whitespaceCharPainter.deactivate(true);
		whitespaceCharPainter = null;
	}
}
