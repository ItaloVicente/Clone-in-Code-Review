package org.eclipse.ui.tests.themes;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.themes.IColorFactory;

public class TestColorFactory implements IColorFactory {

    public static final RGB RGB = new RGB(91, 92, 93);

    @Override
	public RGB createColor() {
        return RGB;
    }

}
