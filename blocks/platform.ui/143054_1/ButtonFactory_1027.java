package org.eclipse.jface.widgets;

import java.util.function.Consumer;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public final class ButtonFactory extends AbstractControlFactory<ButtonFactory, Button> {

	private ButtonFactory(int style) {
		super(ButtonFactory.class, (Composite parent) -> new Button(parent, style));
	}

	public static ButtonFactory newButton(int style) {
		return new ButtonFactory(style);
	}

	public ButtonFactory text(String text) {
		addProperty(b -> b.setText(text));
		return this;
	}

	public ButtonFactory image(Image image) {
		addProperty(b -> b.setImage(image));
		return this;
	}

	public ButtonFactory onSelect(Consumer<SelectionEvent> consumer) {
		addProperty(c -> c.addSelectionListener(SelectionListener.widgetSelectedAdapter(consumer)));
		return this;
	}
}
