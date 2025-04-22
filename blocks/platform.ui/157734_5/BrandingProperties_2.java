package org.eclipse.e4.ui.internal.dialogs.about;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.program.Program;

public class AboutText {

	private String aboutProperty;
	private StyledText styledText;

	private Cursor handCursor;
	private Cursor busyCursor;

	private boolean mouseDown = false;
	private boolean dragEvent = false;

	private AboutItem item;

	public AboutText(AboutItem item) {
		this.setItem(item);
	}

	public AboutText(StyledText text, AboutItem item) {
		this.styledText = text;
		this.setItem(item);
		createCursors();
		addListeners();
	}

	public AboutText(String aboutProperty) {
		this.aboutProperty = aboutProperty;
	}

	private void createAboutItem() {
		if (aboutProperty == null || aboutProperty.isEmpty()) {
			return;
		}

		List<HyperlinkRange> linkRanges = new ArrayList<>();
		List<String> links = new ArrayList<>();


		int urlSeparatorOffset = aboutProperty.indexOf("://"); //$NON-NLS-1$
		while (urlSeparatorOffset >= 0) {

			boolean startDoubleQuote = false;

			int urlOffset = urlSeparatorOffset;
			char ch;
			do {
				urlOffset--;
				ch = ' ';
				if (urlOffset > -1) {
					ch = aboutProperty.charAt(urlOffset);
				}
				startDoubleQuote = ch == '"';
			} while (Character.isUnicodeIdentifierStart(ch));
			urlOffset++;

			StringTokenizer tokenizer = new StringTokenizer(aboutProperty.substring(urlSeparatorOffset + 3),
					" \t\n\r\f<>", false); //$NON-NLS-1$
			if (!tokenizer.hasMoreTokens()) {
				return;
			}

			int urlLength = tokenizer.nextToken().length() + 3 + urlSeparatorOffset - urlOffset;

			if (startDoubleQuote) {
				int endOffset = -1;
				int nextDoubleQuote = aboutProperty.indexOf('"', urlOffset);
				int nextWhitespace = aboutProperty.indexOf(' ', urlOffset);
				if (nextDoubleQuote != -1 && nextWhitespace != -1) {
					endOffset = Math.min(nextDoubleQuote, nextWhitespace);
				} else if (nextDoubleQuote != -1) {
					endOffset = nextDoubleQuote;
				} else if (nextWhitespace != -1) {
					endOffset = nextWhitespace;
				}
				if (endOffset != -1) {
					urlLength = endOffset - urlOffset;
				}
			}

			linkRanges.add(new HyperlinkRange(urlOffset, urlLength));
			links.add(aboutProperty.substring(urlOffset, urlOffset + urlLength));

			urlSeparatorOffset = aboutProperty.indexOf("://", urlOffset + urlLength + 1); //$NON-NLS-1$
		}

		setItem(new AboutItem(aboutProperty, linkRanges, links));
	}

	private void createCursors() {
		handCursor = new Cursor(styledText.getDisplay(), SWT.CURSOR_HAND);
		busyCursor = new Cursor(styledText.getDisplay(), SWT.CURSOR_WAIT);
		styledText.addDisposeListener((DisposeEvent e) -> {
			handCursor.dispose();
			handCursor = null;
			busyCursor.dispose();
			busyCursor = null;
		});
	}

	protected void addListeners() {
		styledText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (e.button != 1) {
					return;
				}
				mouseDown = true;
			}

			@Override
			public void mouseUp(MouseEvent e) {
				mouseDown = false;
				int offset = styledText.getCaretOffset();
				if (dragEvent) {
					dragEvent = false;
					if (item != null && item.isLinkAt(offset)) {
						styledText.setCursor(handCursor);
					}
				} else if (item != null && item.isLinkAt(offset)) {
					styledText.setCursor(busyCursor);
					Program.launch(item.getLinkAt(offset));
					StyleRange selectionRange = getCurrentRange();
					styledText.setSelectionRange(selectionRange.start, selectionRange.length);
					styledText.setCursor(null);
				}
			}
		});

		styledText.addMouseMoveListener((MouseEvent e) -> {
			if (mouseDown) {
				if (!dragEvent) {
					StyledText text = (StyledText) e.widget;
					text.setCursor(null);
				}
				dragEvent = true;
				return;
			}
			StyledText text = (StyledText) e.widget;
			int offset = -1;
			try {
				offset = text.getOffsetAtPoint(new Point(e.x, e.y));
			} catch (IllegalArgumentException ex) {
			}
			if (offset == -1) {
				text.setCursor(null);
			} else if (item != null && item.isLinkAt(offset)) {
				text.setCursor(handCursor);
			} else {
				text.setCursor(null);
			}
		});

		styledText.addTraverseListener(new TraverseListener() {
			@Override
			public void keyTraversed(TraverseEvent e) {
				switch (e.detail) {
				case SWT.TRAVERSE_ESCAPE:
					e.doit = true;
					break;
				case SWT.TRAVERSE_TAB_NEXT:
					Point nextSelection = styledText.getSelection();
					int charCount = styledText.getCharCount();
					if ((nextSelection.x == charCount) && (nextSelection.y == charCount)) {
						styledText.setSelection(0);
					}
					StyleRange nextRange = findNextRange();
					if (nextRange == null) {
						styledText.setSelection(0);
						e.doit = true;
					} else {
						styledText.setSelectionRange(nextRange.start, nextRange.length);
						e.doit = true;
						e.detail = SWT.TRAVERSE_NONE;
					}
					break;
				case SWT.TRAVERSE_TAB_PREVIOUS:
					Point previousSelection = styledText.getSelection();
					if ((previousSelection.x == 0) && (previousSelection.y == 0)) {
						styledText.setSelection(styledText.getCharCount());
					}
					StyleRange previousRange = findPreviousRange();
					if (previousRange == null) {
						styledText.setSelection(styledText.getCharCount());
						e.doit = true;
					} else {
						styledText.setSelectionRange(previousRange.start, previousRange.length);
						e.doit = true;
						e.detail = SWT.TRAVERSE_NONE;
					}
					break;
				default:
					break;
				}
			}
		});

		styledText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				StyledText text = (StyledText) event.widget;
				if (event.character == ' ' || event.character == SWT.CR) {
					if (item != null) {
						int offset = text.getSelection().x + 1;

						if (item.isLinkAt(offset)) {
							text.setCursor(busyCursor);
							Program.launch(item.getLinkAt(offset));
							StyleRange selectionRange = getCurrentRange();
							text.setSelectionRange(selectionRange.start, selectionRange.length);
							text.setCursor(null);
						}
					}
					return;
				}
			}
		});
	}

	public AboutItem getAboutItem() {
		if (item == null) {
			createAboutItem();
		}
		return item;
	}

	private void setItem(AboutItem item) {
		this.item = item;
		if (item != null && styledText != null) {
			styledText.setText(item.getText());
			setLinkRanges(item.getLinkRanges());
		}
	}

	private StyleRange getCurrentRange() {
		StyleRange[] ranges = styledText.getStyleRanges();
		int currentSelectionEnd = styledText.getSelection().y;
		int currentSelectionStart = styledText.getSelection().x;

		return Arrays.stream(ranges).filter(
				range -> (currentSelectionStart >= range.start && currentSelectionEnd <= range.start + range.length))
				.findFirst().orElse(null);
	}

	private StyleRange findNextRange() {
		StyleRange[] ranges = styledText.getStyleRanges();
		int currentSelectionEnd = styledText.getSelection().y;

		return Arrays.stream(ranges).filter(range -> (range.start >= currentSelectionEnd)).findFirst().orElse(null);
	}

	private StyleRange findPreviousRange() {
		StyleRange[] ranges = styledText.getStyleRanges();
		int currentSelectionStart = styledText.getSelection().x;

		for (int i = ranges.length - 1; i > -1; i--) {
			if ((ranges[i].start + ranges[i].length - 1) < currentSelectionStart) {
				return ranges[i];
			}
		}
		return null;
	}

	private void setLinkRanges(final List<HyperlinkRange> linkRanges) {
		Color fg = JFaceColors.getHyperlinkText(styledText.getShell().getDisplay());
		if (fg == null) {
			JFaceResources.getColorRegistry().put(JFacePreferences.HYPERLINK_COLOR, new RGB(0, 0, 128));
			fg = JFaceColors.getHyperlinkText(styledText.getShell().getDisplay());
		}
		for (HyperlinkRange linkRange : linkRanges) {
			StyleRange range = new StyleRange(linkRange.getOffset(), linkRange.getLength(), fg, null);
			styledText.setStyleRange(range);
		}
	}
}
