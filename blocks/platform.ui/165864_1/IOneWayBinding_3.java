package org.eclipse.core.databinding.bind;

interface IModelBinding<T> {

	T getModelValue();

	void setModelValue(T newValue);

	void removeModelListener();
}
