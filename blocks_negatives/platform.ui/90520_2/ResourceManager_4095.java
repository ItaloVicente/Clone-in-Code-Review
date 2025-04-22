/*******************************************************************************
 * Copyright (c) 2004, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jface.resource;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.RGB;

/**
 * Describes a color by its RGB values.
 *
 * @since 3.1
 */
class RGBColorDescriptor extends ColorDescriptor {

    private RGB color;

    /**
     * Color being copied, or null if none
     */
    private Color originalColor = null;

    /**
     * Creates a new RGBColorDescriptor given some RGB values
     *
     * @param color RGB values (not null)
     */
    public RGBColorDescriptor(RGB color) {
        this.color = color;
    }

	/**
     * Creates a new RGBColorDescriptor that describes an existing color.
     *
     * @since 3.1
     *
     * @param originalColor a color to describe
     */
    public RGBColorDescriptor(Color originalColor) {
        this(originalColor.getRGB());
        this.originalColor = originalColor;
    }

    @Override
	public boolean equals(Object obj) {
        if (obj instanceof RGBColorDescriptor) {
            RGBColorDescriptor other = (RGBColorDescriptor) obj;

            return other.color.equals(color) && other.originalColor == originalColor;
        }

        return false;
    }

    @Override
	public int hashCode() {
        return color.hashCode();
    }

    @Override
	public Color createColor(Device device) {
        if (originalColor != null) {
            if (originalColor.getDevice() == device) {
                return originalColor;
            }
        }

        return new Color(device, color);
    }

    @Override
	public void destroyColor(Color toDestroy) {
        if (toDestroy == originalColor) {
            return;
        }

        toDestroy.dispose();
    }
}
