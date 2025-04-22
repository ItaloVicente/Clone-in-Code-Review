package org.eclipse.jface.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public abstract class ControlFactory<F extends ControlFactory<?,?>, C extends Control> {
	private Class<F> factoryClass;

	private int style;

	private String tooltipText;
	private Boolean enabled;

	private Object layoutData;

	protected ControlFactory(Class<F> factoryClass, int style) {
		this.factoryClass = factoryClass;
		this.style = style;
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
		C control = createControl(parent, style);
		applyProperties(control);
		return control;
	}

	protected abstract C createControl(Composite parent, int style);

	protected void applyProperties(C control) {
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
}
