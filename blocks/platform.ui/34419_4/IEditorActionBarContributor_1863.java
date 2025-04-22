package org.eclipse.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IDelayedLabelDecorator;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

public interface IDecoratorManager extends IDelayedLabelDecorator{

    ILabelDecorator getLabelDecorator();

    IBaseLabelProvider getBaseLabelProvider(String decoratorId);

    ILabelDecorator getLabelDecorator(String decoratorId);

    @Deprecated
	ILightweightLabelDecorator getLightweightLabelDecorator(String decoratorId);

    boolean getEnabled(String decoratorId);

    void setEnabled(String decoratorId, boolean enabled) throws CoreException;

    void update(String decoratorId);

}
