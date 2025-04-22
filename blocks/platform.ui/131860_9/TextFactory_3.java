package org.eclipse.jface.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class TextFactory extends ControlFactory<TextFactory, Text> {

	private String text;
	private String message;
	private int limit = -1;
	private Collection<SelectionListener> selectionListeners = new ArrayList<>();
	private Collection<ModifyListener> modifyListeners = new ArrayList<>();
	private Collection<VerifyListener> verifyListeners = new ArrayList<>();

	private TextFactory(int style) {
		super(TextFactory.class, (Composite parent) -> new Text(parent, style));
	}

	public static TextFactory newText(int style) {
		return new TextFactory(style);
	}

	public TextFactory text(String text) {
		this.text = text;
		return this;
	}

	public TextFactory message(String message) {
		this.message = message;
		return this;
	}

	public TextFactory limitTo(int limit) {
		this.limit = limit;
		return this;
	}

	public TextFactory onSelect(Consumer<SelectionEvent> consumer) {
		this.selectionListeners.add(SelectionListener.widgetSelectedAdapter(consumer));
		return this;
	}

	public TextFactory onModify(ModifyListener listener) {
		this.modifyListeners.add(listener);
		return this;
	}

	public TextFactory onVerify(VerifyListener listener) {
		this.verifyListeners.add(listener);
		return this;
	}

	@Override
	protected void applyProperties(Text control) {
		if (this.text != null) {
			control.setText(this.text);
		}
		if (this.limit > -1) {
			control.setTextLimit(this.limit);
		}
		if (this.message != null) {
			control.setMessage(this.message);
		}
		this.selectionListeners.forEach(l -> control.addSelectionListener(l));
		this.modifyListeners.forEach(l -> control.addModifyListener(l));
		this.verifyListeners.forEach(l -> control.addVerifyListener(l));
	}
}
