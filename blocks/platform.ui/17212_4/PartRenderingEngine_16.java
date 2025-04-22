package org.eclipse.e4.ui.internal.css.swt.definition;

public interface IThemeElementDefinitionOverridable<T> {
	String getId();
	
	void setValue(T data);
	
	T getValue();	
	
	boolean isOverridden();
}
