        StringTokenizer stok = new StringTokenizer(value, ",); //$NON-NLS-1$
-
-        try {
-            String red = stok.nextToken().trim();
-            String green = stok.nextToken().trim();
-            String blue = stok.nextToken().trim();
-            int rval = 0, gval = 0, bval = 0;
-            try {
-                rval = Integer.parseInt(red);
-                gval = Integer.parseInt(green);
-                bval = Integer.parseInt(blue);
-            } catch (NumberFormatException e) {
-                throw new DataFormatException(e.getMessage());
-            }
-            return new RGB(rval, gval, bval);
-        } catch (IllegalArgumentException e) {
-            throw new DataFormatException(e.getMessage());
-        } catch (NoSuchElementException e) {
-            throw new DataFormatException(e.getMessage());
-        }
-    }
-
-    /**
-     * Converts the given value into an SWT RGB color value.
-     * Returns the given default value if the
-     * value does not represent an RGB color value.
-     *
-     * @param value the value to be converted
-     * @param dflt the default value
-     * @return the value as a RGB color value, or the default value
-     */
-    public static RGB asRGB(String value, RGB dflt) {
-        try {
-            return asRGB(value);
-        } catch (DataFormatException e) {
-            return dflt;
-        }
-    }
-
-    /**
-     * Converts the given double value to a string.
-     * Equivalent to <code>String.valueOf(value)</code>.
-     *
-     * @param value the double value
-     * @return the string representing the given double
-     */
-    public static String asString(double value) {
-        return String.valueOf(value);
-    }
-
-    /**
-     * Converts the given float value to a string.
-     * Equivalent to <code>String.valueOf(value)</code>.
-     *
-     * @param value the float value
-     * @return the string representing the given float
-     */
-    public static String asString(float value) {
-        return String.valueOf(value);
-    }
-
-    /**
-     * Converts the given int value to a string.
-     * Equivalent to <code>String.valueOf(value)</code>.
-     *
-     * @param value the int value
-     * @return the string representing the given int
-     */
-    public static String asString(int value) {
-        return String.valueOf(value);
-    }
-
-    /**
-     * Converts the given long value to a string.
-     * Equivalent to <code>String.valueOf(value)</code>.
-     *
-     * @param value the long value
-     * @return the string representing the given long
-     */
-    public static String asString(long value) {
-        return String.valueOf(value);
-    }
-
-    /**
-     * Converts the given boolean object to a string.
-     * Equivalent to <code>String.valueOf(value.booleanValue())</code>.
-     *
-     * @param value the boolean object
-     * @return the string representing the given boolean value
-     */
-    public static String asString(Boolean value) {
-        Assert.isNotNull(value);
-        return String.valueOf(value.booleanValue());
-    }
-
-    /**
-     * Converts the given double object to a string.
-     * Equivalent to <code>String.valueOf(value.doubleValue())</code>.
-     *
-     * @param value the double object
-     * @return the string representing the given double value
-     */
-    public static String asString(Double value) {
-        Assert.isNotNull(value);
-        return String.valueOf(value.doubleValue());
-    }
-
-    /**
-     * Converts the given float object to a string.
-     * Equivalent to <code>String.valueOf(value.floatValue())</code>.
-     *
-     * @param value the float object
-     * @return the string representing the given float value
-     */
-    public static String asString(Float value) {
-        Assert.isNotNull(value);
-        return String.valueOf(value.floatValue());
-    }
-
-    /**
-     * Converts the given integer object to a string.
-     * Equivalent to <code>String.valueOf(value.intValue())</code>.
-     *
-     * @param value the integer object
-     * @return the string representing the given integer value
-     */
-    public static String asString(Integer value) {
-        Assert.isNotNull(value);
-        return String.valueOf(value.intValue());
-    }
-
-    /**
-     * Converts the given long object to a string.
-     * Equivalent to <code>String.valueOf(value.longValue())</code>.
-     *
-     * @param value the long object
-     * @return the string representing the given long value
-     */
-    public static String asString(Long value) {
-        Assert.isNotNull(value);
-        return String.valueOf(value.longValue());
-    }
-
-    /**
-     * Converts a font data array  to a string. The string representation is
-     * that of asString(FontData) seperated by ';'
-     *
-     * @param value The font data.
-     * @return The string representation of the font data arra.
-     * @since 3.0
-     */
-    public static String asString(FontData[] value) {
-        StringBuilder buffer = new StringBuilder();
-        for (int i = 0; i < value.length; i++) {
-            buffer.append(asString(value[i]));
-            if (i != value.length - 1) {
+		StringTokenizer stok = new StringTokenizer(value, ,); //$NON-NLS-1$
+
+		try {
+			String red = stok.nextToken().trim();
+			String green = stok.nextToken().trim();
+			String blue = stok.nextToken().trim();
+			int rval = 0, gval = 0, bval = 0;
+			try {
+				rval = Integer.parseInt(red);
+				gval = Integer.parseInt(green);
+				bval = Integer.parseInt(blue);
+			} catch (NumberFormatException e) {
+				throw new DataFormatException(e.getMessage());
+			}
+			return new RGB(rval, gval, bval);
+		} catch (IllegalArgumentException e) {
+			throw new DataFormatException(e.getMessage());
+		} catch (NoSuchElementException e) {
+			throw new DataFormatException(e.getMessage());
+		}
+	}
+
+	/**
+	 * Converts the given value into an SWT RGB color value.
+	 * Returns the given default value if the
+	 * value does not represent an RGB color value.
+	 *
+	 * @param value the value to be converted
+	 * @param dflt the default value
+	 * @return the value as a RGB color value, or the default value
+	 */
+	public static RGB asRGB(String value, RGB dflt) {
+		try {
+			return asRGB(value);
+		} catch (DataFormatException e) {
+			return dflt;
+		}
+	}
+
+	/**
+	 * Converts the given double value to a string.
+	 * Equivalent to <code>String.valueOf(value)</code>.
+	 *
+	 * @param value the double value
+	 * @return the string representing the given double
+	 */
+	public static String asString(double value) {
+		return String.valueOf(value);
+	}
+
+	/**
+	 * Converts the given float value to a string.
+	 * Equivalent to <code>String.valueOf(value)</code>.
+	 *
+	 * @param value the float value
+	 * @return the string representing the given float
+	 */
+	public static String asString(float value) {
+		return String.valueOf(value);
+	}
+
+	/**
+	 * Converts the given int value to a string.
+	 * Equivalent to <code>String.valueOf(value)</code>.
+	 *
+	 * @param value the int value
+	 * @return the string representing the given int
+	 */
+	public static String asString(int value) {
+		return String.valueOf(value);
+	}
+
+	/**
+	 * Converts the given long value to a string.
+	 * Equivalent to <code>String.valueOf(value)</code>.
+	 *
+	 * @param value the long value
+	 * @return the string representing the given long
+	 */
+	public static String asString(long value) {
+		return String.valueOf(value);
+	}
+
+	/**
+	 * Converts the given boolean object to a string.
+	 * Equivalent to <code>String.valueOf(value.booleanValue())</code>.
+	 *
+	 * @param value the boolean object
+	 * @return the string representing the given boolean value
+	 */
+	public static String asString(Boolean value) {
+		Assert.isNotNull(value);
+		return String.valueOf(value.booleanValue());
+	}
+
+	/**
+	 * Converts the given double object to a string.
+	 * Equivalent to <code>String.valueOf(value.doubleValue())</code>.
+	 *
+	 * @param value the double object
+	 * @return the string representing the given double value
+	 */
+	public static String asString(Double value) {
+		Assert.isNotNull(value);
+		return String.valueOf(value.doubleValue());
+	}
+
+	/**
+	 * Converts the given float object to a string.
+	 * Equivalent to <code>String.valueOf(value.floatValue())</code>.
+	 *
+	 * @param value the float object
+	 * @return the string representing the given float value
+	 */
+	public static String asString(Float value) {
+		Assert.isNotNull(value);
+		return String.valueOf(value.floatValue());
+	}
+
+	/**
+	 * Converts the given integer object to a string.
+	 * Equivalent to <code>String.valueOf(value.intValue())</code>.
+	 *
+	 * @param value the integer object
+	 * @return the string representing the given integer value
+	 */
+	public static String asString(Integer value) {
+		Assert.isNotNull(value);
+		return String.valueOf(value.intValue());
+	}
+
+	/**
+	 * Converts the given long object to a string.
+	 * Equivalent to <code>String.valueOf(value.longValue())</code>.
+	 *
+	 * @param value the long object
+	 * @return the string representing the given long value
+	 */
+	public static String asString(Long value) {
+		Assert.isNotNull(value);
+		return String.valueOf(value.longValue());
+	}
+
+	/**
+	 * Converts a font data array  to a string. The string representation is
+	 * that of asString(FontData) seperated by ';'
+	 *
+	 * @param value The font data.
+	 * @return The string representation of the font data arra.
+	 * @since 3.0
+	 */
+	public static String asString(FontData[] value) {
+		StringBuilder buffer = new StringBuilder();
+		for (int i = 0; i < value.length; i++) {
+			buffer.append(asString(value[i]));
+			if (i != value.length - 1) {
 				buffer.append(FONT_SEPARATOR);
 			}
-        }
-        return buffer.toString();
-    }
-
-    /**
-     * Converts a font data object to a string. The string representation is
-     * font name-style-height" (for example "Times New Roman-bold-36").
     * @param value The font data.
     * @return The string representation of the font data object.
     */
    public static String asString(FontData value) {
        Assert.isNotNull(value);
        StringBuilder buffer = new StringBuilder();
        buffer.append(value.getName());
        buffer.append(SEPARATOR);
        int style = value.getStyle();
        boolean bold = (style & SWT.BOLD) == SWT.BOLD;
        boolean italic = (style & SWT.ITALIC) == SWT.ITALIC;
        if (bold && italic) {
            buffer.append(BOLD_ITALIC);
        } else if (bold) {
            buffer.append(BOLD);
        } else if (italic) {
            buffer.append(ITALIC);
        } else {
            buffer.append(REGULAR);
        }

        buffer.append(SEPARATOR);
        buffer.append(value.getHeight());
        return buffer.toString();
    }

    /**
