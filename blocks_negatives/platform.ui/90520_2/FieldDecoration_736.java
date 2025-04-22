/*******************************************************************************
 * Copyright (c) 2006, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jface.fieldassist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

/**
 * FieldAssistColors defines protocol for retrieving colors that can be used to
 * provide visual cues with fields. For consistency with JFace dialogs and
 * wizards, it is recommended that FieldAssistColors is used when colors are
 * used to annotate fields.
 * <p>
 * Color resources that are returned using methods in this class are maintained
 * in the JFace color registries, or by SWT. Users of any color resources
 * provided by this class are not responsible for the lifecycle of the color.
 * Colors provided by this class should never be disposed by clients. In some
 * cases, clients are provided information, such as RGB values, in order to
 * create their own color resources. In these cases, the client should manage
 * the lifecycle of any created resource.
 *
 * @since 3.2
 * @deprecated As of 3.3, this class is no longer necessary.
 */
@Deprecated
public class FieldAssistColors {

	private static boolean DEBUG = false;

	/*
	 * Keys are background colors, values are the color with the alpha value
	 * applied
	 */
	private static Map<Color, Color> requiredFieldColorMap = new HashMap<>();

	/*
	 * Keys are colors we have created, values are the displays on which they
	 * were created.
	 */
	private static Map<Color, Display> displays = new HashMap<>();

	/**
	 * Compute the RGB of the color that should be used for the background of a
	 * control to indicate that the control has an error. Because the color
	 * suitable for indicating an error depends on the colors set into the
	 * control, this color is always computed dynamically and provided as an RGB
	 * value. Clients who use this RGB to create a Color resource are
	 * responsible for managing the life cycle of the color.
	 * <p>
	 * This color is computed dynamically each time that it is queried. Clients
	 * should typically call this method once, create a color from the RGB
	 * provided, and dispose of the color when finished using it.
	 *
	 * @param control
	 *            the control for which the background color should be computed.
	 * @return the RGB value indicating a background color appropriate for
	 *         indicating an error in the control.
	 */
	public static RGB computeErrorFieldBackgroundRGB(Control control) {
		/*
		 * Use a 10% alpha of the error color applied on top of the widget
		 * background color.
		 */
		Color dest = control.getBackground();
		Color src = JFaceColors.getErrorText(control.getDisplay());
		int destRed = dest.getRed();
		int destGreen = dest.getGreen();
		int destBlue = dest.getBlue();

		int alpha = (int) (0xFF * 0.10f);
		destRed += (src.getRed() - destRed) * alpha / 0xFF;
		destGreen += (src.getGreen() - destGreen) * alpha / 0xFF;
		destBlue += (src.getBlue() - destBlue) * alpha / 0xFF;

		return new RGB(destRed, destGreen, destBlue);
	}

	/**
	 * Return the color that should be used for the background of a control to
	 * indicate that the control is a required field and does not have content.
	 * <p>
	 * This color is managed by FieldAssistResources and should never be
	 * disposed by clients.
	 *
	 * @param control
	 *            the control on which the background color will be used.
	 * @return the color used to indicate that a field is required.
	 */
	public static Color getRequiredFieldBackgroundColor(Control control) {
		final Display display = control.getDisplay();

		if (display.getHighContrast()) {
			return control.getBackground();
		}

		Object storedColor = requiredFieldColorMap.get(control.getBackground());
		if (storedColor != null) {
			return (Color) storedColor;
		}

		Color dest = control.getBackground();
		Color src = display.getSystemColor(SWT.COLOR_YELLOW);
		int destRed = dest.getRed();
		int destGreen = dest.getGreen();
		int destBlue = dest.getBlue();

		int alpha = (int) (0xFF * 0.15f);
		destRed += (src.getRed() - destRed) * alpha / 0xFF;
		destGreen += (src.getGreen() - destGreen) * alpha / 0xFF;
		destBlue += (src.getBlue() - destBlue) * alpha / 0xFF;

		Color color = new Color(display, destRed, destGreen, destBlue);
		requiredFieldColorMap.put(dest, color);
		if (!displays.containsValue(display)) {
			display.disposeExec(() -> disposeColors(display));
		}
		displays.put(color, display);
		return color;
	}

	/*
	 * Dispose any colors that were allocated for the given display.
	 */
	private static void disposeColors(Display display) {
		List<Color> toBeRemoved = new ArrayList<>(1);

		if (DEBUG) {
			System.out
		}

		for (Entry<Color, Display> entry : displays.entrySet()) {
			Color color = entry.getKey();
			if (displays.get(color).equals(display)) {
				toBeRemoved.add(color);

				List<Color> toBeRemovedFromRequiredMap = new ArrayList<>(1);
				for (Entry<Color, Color> colorMapEntry : requiredFieldColorMap.entrySet()) {
					Color bgColor = colorMapEntry.getKey();
					if (colorMapEntry.getValue().equals(color)) {
						toBeRemovedFromRequiredMap.add(bgColor);
					}
				}
				for (Color toRemove : toBeRemovedFromRequiredMap) {
					requiredFieldColorMap.remove(toRemove);
				}
			}
		}
		for (Color color : toBeRemoved) {
			displays.remove(color);
			if (DEBUG) {
			}
			color.dispose();
		}
		if (DEBUG) {
			System.out
		}
	}

}
