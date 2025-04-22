package org.eclipse.ui.themes;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import org.eclipse.jface.resource.DataFormatException;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public final class ColorUtil {

	private static Field[] cachedFields;
	
	private static RGB process(String value) {
		Field [] fields = getFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				if (field.getName().equals(value)) {
					return getSystemColor(field.getInt(null));
				}
			}
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
		return getSystemColor(SWT.COLOR_BLACK);
	}

	private static Field[] getFields() {
		if (cachedFields == null) {
			Class<SWT> clazz = SWT.class;
			Field[] allFields = clazz.getDeclaredFields();
			ArrayList<Field> applicableFields = new ArrayList<Field>(allFields.length);
			
			for (int i = 0; i < allFields.length; i++) {
				Field field = allFields[i];
				if (field.getType() == Integer.TYPE
						&& Modifier.isStatic(field.getModifiers())
						&& Modifier.isPublic(field.getModifiers())
						&& Modifier.isFinal(field.getModifiers())
						&& field.getName().startsWith("COLOR")) { //$NON-NLS-1$
				
					applicableFields.add(field);
				}
			}
			cachedFields = applicableFields.toArray(new Field[applicableFields.size()]);
		}
		return cachedFields;
	}

	public static RGB blend(RGB c1, RGB c2, int ratio) {
		int r = blend(c1.red, c2.red, ratio);
		int g = blend(c1.green, c2.green, ratio);
		int b = blend(c1.blue, c2.blue, ratio);
		return new RGB(r, g, b);
	}

	private static int blend(int v1, int v2, int ratio) {
		int b = (ratio * v1 + (100 - ratio) * v2) / 100;
		return Math.min(255, b);
	}
	
	public static RGB blend(RGB val1, RGB val2) {
		int red = blend(val1.red, val2.red);
		int green = blend(val1.green, val2.green);
		int blue = blend(val1.blue, val2.blue);
		return new RGB(red, green, blue);
	}

	private static int blend(int temp1, int temp2) {
		return (Math.abs(temp1 - temp2) / 2) + Math.min(temp1, temp2);
	}

	private static RGB getSystemColor(int colorId) {
		return Display.getCurrent().getSystemColor(colorId).getRGB();
	}

	public static RGB getColorValue(String rawValue) throws DataFormatException {
		if (rawValue == null) {
			return null;
		}

		rawValue = rawValue.trim();

		if (!isDirectValue(rawValue)) {
			return process(rawValue);
		}

		return StringConverter.asRGB(rawValue);
	}

	public static RGB[] getColorValues(String[] rawValues) {
		RGB[] values = new RGB[rawValues.length];
		for (int i = 0; i < rawValues.length; i++) {
			values[i] = getColorValue(rawValues[i]);
		}
		return values;
	}

	private static boolean isDirectValue(String rawValue) {
		return rawValue.indexOf(',') >= 0;
	}

	private ColorUtil() {
	}
}
