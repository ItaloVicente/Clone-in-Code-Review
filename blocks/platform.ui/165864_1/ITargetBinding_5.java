package org.eclipse.core.databinding.bind;

interface IOneWayModelBinding<T> {

	T getModelValue();

	void removeModelListener();
}
