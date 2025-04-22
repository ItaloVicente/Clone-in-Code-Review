package org.eclipse.ui;

import org.eclipse.core.runtime.IAdaptable;

public interface IElementFactory {
    public IAdaptable createElement(IMemento memento);
}
