
package org.eclipse.jface.examples.databinding.mask;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.jface.examples.databinding.mask.internal.EditMaskParser;
import org.eclipse.jface.examples.databinding.mask.internal.SWTUtil;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

public class EditMask {

	public static final String FIELD_TEXT = "text";
	public static final String FIELD_RAW_TEXT = "rawText";
	public static final String FIELD_COMPLETE = "complete";
	protected Text text;
	protected EditMaskParser editMaskParser;
	private boolean fireChangeOnKeystroke = true;
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	protected String oldValidRawText = "";
	protected String oldValidText = "";

	public EditMask(Text text) {
		this.text = text;
	}

	public Text getControl() {
		return this.text;
	}

	public void setMask(String editMask) {
		editMaskParser = new EditMaskParser(editMask);
		text.addVerifyListener(verifyListener);
		text.addFocusListener(focusListener);
		text.addDisposeListener(disposeListener);
		updateTextField.run();
		oldValidText = text.getText();
		oldValidRawText = editMaskParser.getRawResult();
	}

    public void setText(String string) {
    	String oldValue = text.getText();
    	if (editMaskParser != null) {
			editMaskParser.setInput(string);
			String formattedResult = editMaskParser.getFormattedResult();
			text.setText(formattedResult);
			firePropertyChange(FIELD_TEXT, oldValue, formattedResult);
    	} else {
    		text.setText(string);
			firePropertyChange(FIELD_TEXT, oldValue, string);
    	}
		oldValidText = text.getText();
		oldValidRawText = editMaskParser.getRawResult();
	}

	public String getText() {
		if (editMaskParser != null) {
			return editMaskParser.getFormattedResult();
		}
		return text.getText();
	}

	public void setRawText(String string)  {
		if (string == null) {
			string = "";
		}
		if (editMaskParser != null) {
			String oldValue = editMaskParser.getRawResult();
			editMaskParser.setInput(string);
			text.setText(editMaskParser.getFormattedResult());
			firePropertyChange(FIELD_RAW_TEXT, oldValue, string);
		} else {
	    	String oldValue = text.getText();
    		text.setText(string);
			firePropertyChange(FIELD_RAW_TEXT, oldValue, string);
		}
		oldValidText = text.getText();
		oldValidRawText = editMaskParser.getRawResult();
	}

	public String getRawText() {
		if (editMaskParser != null) {
			return editMaskParser.getRawResult();
		}
		return text.getText();
	}

	public boolean isComplete() {
		if (editMaskParser == null) {
			return true;
		}
		return editMaskParser.isComplete();
	}

	public char getPlaceholder() {
		if (editMaskParser == null) {
			throw new IllegalArgumentException("Have to set an edit mask first");
		}
		return editMaskParser.getPlaceholder();
	}

	public void setPlaceholder(char placeholder) {
		if (editMaskParser == null) {
			throw new IllegalArgumentException("Have to set an edit mask first");
		}
		editMaskParser.setPlaceholder(placeholder);
		updateTextField.run();
		oldValidText = text.getText();
	}

	public boolean isFireChangeOnKeystroke() {
		return fireChangeOnKeystroke;
	}

	public void setFireChangeOnKeystroke(boolean fireChangeOnKeystroke) {
		this.fireChangeOnKeystroke = fireChangeOnKeystroke;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName,
				listener);
	}

	private boolean isEitherValueNotNull(Object oldValue, Object newValue) {
		return oldValue != null || newValue != null;
	}

	private void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		if (isEitherValueNotNull(oldValue, newValue)) {
			propertyChangeSupport.firePropertyChange(propertyName,
					oldValue, newValue);
		}
	}

	protected boolean updating = false;

	protected int oldSelection = 0;
	protected int selection = 0;
	protected String oldRawText = "";
   protected boolean replacedSelectedText = false;

	private VerifyListener verifyListener = new VerifyListener() {
		@Override
		public void verifyText(VerifyEvent e) {
         if (editMaskParser.isComplete() && // should eventually be .isFull() to account for optional characters
               e.start == e.end &&
               e.text.length() > 0)
         {
            e.doit=false;
            return;
         }

			oldSelection = selection;
			Point selectionRange = text.getSelection();
         selection = selectionRange.x;

			if (!updating) {
   			replacedSelectedText = false;
   			if (selectionRange.y - selectionRange.x > 0 && e.text.length() > 0) {
   			   replacedSelectedText = true;
   			}
            SWTUtil.greedyExec(Display.getCurrent(), updateTextField);
         }
		}
	};

	private Runnable updateTextField = new Runnable() {
		@Override
		public void run() {
			updating = true;
			try {
				if (!text.isDisposed()) {
					Boolean oldIsComplete = new Boolean(editMaskParser.isComplete());

					editMaskParser.setInput(text.getText());
					text.setText(editMaskParser.getFormattedResult());
					String newRawText = editMaskParser.getRawResult();

					updateSelectionPosition(newRawText);
					firePropertyChangeEvents(oldIsComplete, newRawText);
				}
			} finally {
				updating = false;
			}
		}

		private void updateSelectionPosition(String newRawText) {

         if (isInsertingNewCharacter(newRawText) || replacedSelectedText) {
            int selectionDelta =
               editMaskParser.getNextInputPosition(oldSelection)
               - oldSelection;
            if (selectionDelta == 0) {
               selectionDelta = editMaskParser.getNextInputPosition(selection)
               - selection;
            }
            selection += selectionDelta;
         }

			if (!newRawText.equals(oldRawText)) { // yep

				int firstIncompletePosition = editMaskParser.getFirstIncompleteInputPosition();
				if (firstIncompletePosition > 0 && selection > firstIncompletePosition)
					selection = firstIncompletePosition;
				text.setSelection(new Point(selection, selection));

			} else { // nothing was accepted by the mask

				if (selection > oldSelection) { // typed an illegal character; backup
					text.setSelection(new Point(selection-1, selection-1));
				} else { // backspaced over a literal; don't interfere with selection position
					text.setSelection(new Point(selection, selection));
				}
			}
			oldRawText = newRawText;
		}

		private boolean isInsertingNewCharacter(String newRawText) {
			return newRawText.length() > oldRawText.length();
		}

		private void firePropertyChangeEvents(Boolean oldIsComplete, String newRawText) {
			Boolean newIsComplete = new Boolean(editMaskParser.isComplete());
			if (!oldIsComplete.equals(newIsComplete)) {
				firePropertyChange(FIELD_COMPLETE, oldIsComplete, newIsComplete);
			}
			if (!newRawText.equals(oldValidRawText)) {
				if ( newIsComplete.booleanValue() || "".equals(newRawText) || fireChangeOnKeystroke) {
					firePropertyChange(FIELD_RAW_TEXT, oldValidRawText, newRawText);
					firePropertyChange(FIELD_TEXT, oldValidText, text.getText());
					oldValidText = text.getText();
					oldValidRawText = newRawText;
				}
			}
		}
	};

	private FocusListener focusListener = new FocusAdapter() {
		@Override
		public void focusGained(FocusEvent e) {
			selection = editMaskParser.getFirstIncompleteInputPosition();
			text.setSelection(selection, selection);
		}
	};

	private DisposeListener disposeListener = new DisposeListener() {
		@Override
		public void widgetDisposed(DisposeEvent e) {
			text.removeVerifyListener(verifyListener);
			text.removeFocusListener(focusListener);
			text.removeDisposeListener(disposeListener);
		}
	};

}
