package org.eclipse.jface.notifications;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.resource.DeviceResourceException;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSValue;

public class GradientColors {

	private final Display display;

	private Color titleText;

	private Color gradientBegin;

	private Color gradientEnd;

	private Color border;

	private final ResourceManager resourceManager;

	public GradientColors(Display display, ResourceManager resourceManager) {
		this.display = display;
		this.resourceManager = resourceManager;

		createColors();
	}

	private void createColors() {
		createBorderColor();
		createGradientColors();
		this.titleText = getColor(this.resourceManager, getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW));
	}

	public Color getGradientBegin() {
		return this.gradientBegin;
	}

	public Color getGradientEnd() {
		return this.gradientEnd;
	}

	public Color getBorder() {
		return this.border;
	}

	public Color getTitleText() {
		return this.titleText;
	}

	private void createBorderColor() {
		RGB tbBorder = getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
		RGB bg = getBackground();

		if (testTwoPrimaryColors(tbBorder, 179, 256)) {
			tbBorder = blend(tbBorder, bg, 70);
		} else if (testTwoPrimaryColors(tbBorder, 120, 180)) {
			tbBorder = blend(tbBorder, bg, 50);
		} else {
			tbBorder = blend(tbBorder, bg, 30);
		}

		this.border = getColor(this.resourceManager, tbBorder);
	}

	private void createGradientColors() {
		RGB titleBg = getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
		RGB bg = getBackground();
		RGB bottom, top;

		if (testTwoPrimaryColors(titleBg, 179, 256)) {
			bottom = blend(titleBg, bg, 30);
			top = bg;
		}

		else if (testTwoPrimaryColors(titleBg, 120, 180)) {
			bottom = blend(titleBg, bg, 20);
			top = bg;
		}

		else {
			bottom = blend(titleBg, bg, 10);
			top = bg;
		}

		this.gradientBegin = getColor(this.resourceManager, top);
		this.gradientEnd = getColor(this.resourceManager, bottom);
	}

	private RGB blend(RGB c1, RGB c2, int ratio) {
		int r = blend(c1.red, c2.red, ratio);
		int g = blend(c1.green, c2.green, ratio);
		int b = blend(c1.blue, c2.blue, ratio);
		return new RGB(r, g, b);
	}

	private int blend(int v1, int v2, int ratio) {
		int b = (ratio * v1 + (100 - ratio) * v2) / 100;
		return Math.min(255, b);
	}

	private boolean testTwoPrimaryColors(RGB rgb, int from, int to) {
		int total = 0;
		if (testPrimaryColor(rgb.red, from, to)) {
			total++;
		}
		if (testPrimaryColor(rgb.green, from, to)) {
			total++;
		}
		if (testPrimaryColor(rgb.blue, from, to)) {
			total++;
		}
		return total >= 2;
	}

	private boolean testPrimaryColor(int value, int from, int to) {
		return value > from && value < to;
	}

	private RGB getSystemColor(int code) {
		return getDisplay().getSystemColor(code).getRGB();
	}

	private Color getImpliedBackground() {
		return this.display.getSystemColor(SWT.COLOR_LIST_BACKGROUND);
	}

	private Display getDisplay() {
		return this.display;
	}

	private Color getColor(ResourceManager manager, RGB rgb) {
		try {
			return manager.createColor(rgb);
		} catch (DeviceResourceException e) {
			return manager.getDevice().getSystemColor(SWT.COLOR_BLACK);
		}
	}

	private RGB getBackground() {
		RGB themeBg = getRGBFromTheme("background-color"); //$NON-NLS-1$
		RGB impliedBg = getImpliedBackground().getRGB();
		if (themeBg == null) {
			return impliedBg;
		}
		if (absoluteDifference(themeBg.red, impliedBg.red) < 40 && absoluteDifference(themeBg.blue,
				impliedBg.blue) < 40
				&& absoluteDifference(themeBg.green, impliedBg.green) < 40) {
			return impliedBg;
		}
		return themeBg;
	}

	private int absoluteDifference(int a, int b) {
		return Math.abs(a - b);
	}

	private RGB getRGBFromTheme(String value) {
		String backgroundColor = getCssValueFromTheme(display, value);
		if (backgroundColor != null) {
			RGB themeColor = getRGBFromCssString(backgroundColor);
			if (themeColor != null) {
				return themeColor;
			}
		}
		return null;
	}

	private static RGB getRGBFromCssString(String cssValue) {
		try {
			if (cssValue.startsWith("rgb(")) { //$NON-NLS-1$
				String rest = cssValue.substring(4, cssValue.length());
				int idx = rest.indexOf("rgb("); //$NON-NLS-1$
				if (idx != -1) {
					rest = rest.substring(idx + 4, rest.length());
				}
				idx = rest.indexOf(")"); //$NON-NLS-1$
				if (idx != -1) {
					rest = rest.substring(0, idx);
				}
				String[] rgbValues = rest.split(","); //$NON-NLS-1$
				if (rgbValues.length == 3) {
					return new RGB(Integer.parseInt(rgbValues[0].trim()), Integer.parseInt(rgbValues[1].trim()),
							Integer.parseInt(rgbValues[2].trim()));
				}
			} else if (cssValue.startsWith("#")) { //$NON-NLS-1$
				String rest = cssValue.substring(1, cssValue.length());
				int idx = rest.indexOf("#"); //$NON-NLS-1$
				if (idx != -1) {
					rest = rest.substring(idx + 1, rest.length());
				}
				if (rest.length() > 5) {
					return new RGB(Integer.parseInt(rest.substring(0, 2), 16),
							Integer.parseInt(rest.substring(2, 4), 16), Integer.parseInt(rest.substring(4, 6), 16));
				}
			}
		} catch (NumberFormatException e) {
			return null;
		}
		return null;
	}

	public static String getCssValueFromTheme(Display display, String value) {

		IWorkbench workbench = PlatformUI.getWorkbench();
		MApplication application = workbench.getService(MApplication.class);
		IEclipseContext context = application.getContext();
		IThemeEngine engine = context.get(IThemeEngine.class);

		CSSStyleDeclaration shellStyle = getStyleDeclaration(engine, display);
		if (shellStyle != null) {
			CSSValue cssValue = shellStyle.getPropertyCSSValue(value);
			if (cssValue != null) {
				return cssValue.getCssText();
			}
		}
		return value;
	}

	private static CSSStyleDeclaration getStyleDeclaration(IThemeEngine themeEngine, Display display) {
		Shell shell = display.getActiveShell();
		CSSStyleDeclaration shellStyle = null;
		if (shell != null) {
			shellStyle = retrieveStyleFromShell(themeEngine, shell);
		} else {
			for (Shell input : display.getShells()) {
				shellStyle = retrieveStyleFromShell(themeEngine, input);
				if (shellStyle != null) {
					break;
				}
			}
		}
		return shellStyle;
	}

	private static CSSStyleDeclaration retrieveStyleFromShell(IThemeEngine themeEngine, Shell shell) {
		return themeEngine.getStyle(shell);
	}
}
