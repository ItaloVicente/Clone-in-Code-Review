package org.eclipse.jface.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public final class ButtonFactory extends ControlFactory<ButtonFactory, Button> {

	private String text;
	private Collection<SelectionListener> selectionListeners = new ArrayList<>();
	private Image image;

	private ButtonFactory(int style) {
		super(ButtonFactory.class, (Composite parent) -> new Button(parent, style));
	}

	public static ButtonFactory newButton(int style) {
		return new ButtonFactory(style);
	}

	public ButtonFactory text(String text) {
		this.text = text;
		return this;
	}

	public ButtonFactory image(Image image) {
		this.image = image;
		return this;
	}

	public ButtonFactory onSelect(Consumer<SelectionEvent> consumer) {
		this.selectionListeners.add(SelectionListener.widgetSelectedAdapter(consumer));
		return this;
	}

	@Override
	protected void applyProperties(Button button) {
		if (this.text != null) {
			button.setText(this.text);
		}
		if (this.image != null) {
			button.setImage(this.image);
		}
		this.selectionListeners.forEach(l -> button.addSelectionListener(l));
	}
}
