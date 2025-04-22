package org.eclipse.ui.internal.decorators;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;


class FullDecoratorDefinition extends DecoratorDefinition {

	ILabelDecorator decorator;


    FullDecoratorDefinition(String identifier, IConfigurationElement element) {
        super(identifier, element);
    }

    protected ILabelDecorator internalGetDecorator() throws CoreException {
        if (labelProviderCreationFailed) {
			return null;
		}

        final CoreException[] exceptions = new CoreException[1];

        if (decorator == null) {
            Platform
                    .run(new SafeRunnable(
                            NLS.bind(WorkbenchMessages.DecoratorManager_ErrorActivatingDecorator, getName() )) { 
                        @Override
						public void run() {
                            try {
                                decorator = (ILabelDecorator) WorkbenchPlugin
                                        .createExtension(
                                                definingElement,
                                                DecoratorDefinition.ATT_CLASS);
                                decorator.addListener(WorkbenchPlugin
                                        .getDefault().getDecoratorManager());
                            } catch (CoreException exception) {
                                exceptions[0] = exception;
                            }
                        }
                    });
        } else {
			return decorator;
		}

        if (decorator == null) {
            this.labelProviderCreationFailed = true;
            setEnabled(false);
        }

        if (exceptions[0] != null) {
			throw exceptions[0];
		}

        return decorator;
    }

    @Override
	protected void refreshDecorator() {
        if (!this.enabled && decorator != null) {
            IBaseLabelProvider cached = decorator;
            decorator = null;
            disposeCachedDecorator(cached);
        }
    }

    Image decorateImage(Image image, Object element) {
        try {
            ILabelDecorator currentDecorator = internalGetDecorator();
            if (currentDecorator != null) {
				return currentDecorator.decorateImage(image, element);
			}

        } catch (CoreException exception) {
            handleCoreException(exception);
        }
        return null;
    }

    String decorateText(String text, Object element) {
        try {
            ILabelDecorator currentDecorator = internalGetDecorator();
            if (currentDecorator != null) {
				return currentDecorator.decorateText(text, element);
			}
        } catch (CoreException exception) {
            handleCoreException(exception);
        }
        return null;
    }

    public ILabelDecorator getDecorator() {
        return decorator;
    }

    @Override
	protected IBaseLabelProvider internalGetLabelProvider()
            throws CoreException {
        return internalGetDecorator();
    }

    @Override
	public boolean isFull() {
        return true;
    }

}
