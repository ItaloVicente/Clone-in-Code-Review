package org.eclipse.jface.widgets;

import java.util.function.Function;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public abstract class ControlFactory<F extends ControlFactory<?,?>, C extends Control> {
	private Class<F> factoryClass;

	private String tooltipText;
	private Boolean enabled;

	private Object layoutData;

	private Function<Composite, C> controlCreator;

	protected ControlFactory(Class<F> factoryClass, Function<Composite, C> controlCreator) {
		this.factoryClass = factoryClass;
		this.controlCreator = controlCreator;
	}

	public F tooltip(String tooltipText) {
		this.tooltipText = tooltipText;
		return factoryClass.cast(this);
	}

	public F enabled(boolean enabled) {
		this.enabled = enabled;
		return factoryClass.cast(this);
	}

	public F layoutData(Object layoutData) {
		this.layoutData = layoutData;
		return factoryClass.cast(this);
	}

	public final C create(Composite parent) {
		C control = controlCreator.apply(parent);
		applyControlProperties(control);
		applyProperties(control);
		return control;
	}

	private void applyControlProperties(C control) {
		if (this.enabled != null) {
			control.setEnabled(this.enabled);
		}
		if (this.tooltipText != null) {
			control.setToolTipText(this.tooltipText);
		}
		if (this.layoutData != null) {
			control.setLayoutData(this.layoutData);
		}
	}

	protected abstract void applyProperties(C control);
}
