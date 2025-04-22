package org.eclipse.ui.internal.decorators;

import org.eclipse.swt.graphics.Image;

class FullImageDecoratorRunnable extends FullDecoratorRunnable {
    Image result = null;

    Image start;

    @Override
	public void run() throws Exception {
        result = decorator.decorateImage(start, element);
    }

    Image getResult() {
        return result;
    }

    void setValues(Image initialImage, Object object,
            FullDecoratorDefinition definition) {
        setValues(object, definition);
        start = initialImage;
        result = null;
    }


	void clearReferences() {
		decorator = null;		
	}
}
