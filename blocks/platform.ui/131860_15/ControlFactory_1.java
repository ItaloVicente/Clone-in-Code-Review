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
		this.enabled = Boolean.valueOf(enabled);
		return factoryClass.cast(this);
	}

	public F layoutData(Object layoutData) {
		this.layoutData = layoutData;
		return factoryClass.cast(this);
	}

	public final C create(Composite parent) {
		C control = controlCreator.apply(parent);
		applyProperties(control);
		return control;
	}

	protected void applyProperties(C control) {
		if (this.enabled != null) {
			control.setEnabled(this.enabled.booleanValue());
		}
		if (this.tooltipText != null) {
			control.setToolTipText(this.tooltipText);
		}
		if (this.layoutData != null) {
			control.setLayoutData(this.layoutData);
		}
	}
}
