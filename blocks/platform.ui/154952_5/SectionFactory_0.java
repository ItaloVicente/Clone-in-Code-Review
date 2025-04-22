package org.eclipse.ui.forms.widgets;

import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.jface.widgets.AbstractCompositeFactory;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;

public final class SectionFactory extends AbstractCompositeFactory<SectionFactory, Section> {

	private SectionFactory(int style) {
		super(SectionFactory.class, (Composite parent) -> new Section(parent, style));
	}

	public static SectionFactory newSection(int style) {
		return new SectionFactory(style);
	}

	public SectionFactory title(String title) {
		addProperty(section -> section.setText(title));
		return this;
	}

	public SectionFactory description(String description) {
		addProperty(section -> section.setDescription(description));
		return this;
	}

	public SectionFactory description(Function<Section, Control> controlFunction) {
		addProperty(section -> section.setDescriptionControl(controlFunction.apply(section)));
		return this;
	}

	public SectionFactory onExpanded(Consumer<ExpansionEvent> consumer) {
		addProperty(
				section -> section.addExpansionListener(IExpansionListener.expansionStateChangingAdapter(consumer)));
		return this;
	}

	public SectionFactory onExpanding(Consumer<ExpansionEvent> consumer) {
		addProperty(
				section -> section.addExpansionListener(IExpansionListener.expansionStateChangingAdapter(consumer)));
		return this;
	}
}
