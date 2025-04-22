package org.eclipse.ui.internal.decorators;

public class FullTextDecoratorRunnable extends FullDecoratorRunnable {
    String result = null;

    String start;

    @Override
	public void run() throws Exception {
        result = decorator.decorateText(start, element);
    }

    String getResult() {
        return result;
    }

    void setValues(String initialString, Object object,
            FullDecoratorDefinition definition) {
        setValues(object, definition);
        start = initialString;
        result = null;
    }

	void clearReferences() {
		decorator = null;		
	}
}
