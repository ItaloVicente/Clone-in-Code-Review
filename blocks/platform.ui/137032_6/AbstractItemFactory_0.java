package org.eclipse.jface.widgets;

import java.util.function.Function;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Widget;

public abstract class AbstractItemFactory<F extends AbstractItemFactory<?, ?, ?>, I extends Item, P extends Widget>
		extends AbstractWidgetFactory<F, I, P> {

	protected AbstractItemFactory(Class<F> factoryClass, Function<P, I> itemCreator) {
		super(factoryClass, itemCreator);
	}

	public F image(Image image) {
		addProperty(i -> i.setImage(image));
		return cast(this);
	}

	public F text(String text) {
		addProperty(i -> i.setText(text));
		return cast(this);
	}
}
