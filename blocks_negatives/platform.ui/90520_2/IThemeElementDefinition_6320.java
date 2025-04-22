/*******************************************************************************
 * Copyright (c) 2004, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.themes;

import java.util.Map;

/**
 * Interface for the Theme descriptors
 *
 * @since 3.0
 */
public interface IThemeDescriptor extends IThemeElementDefinition {

    /**
     * Returns the color overrides for this theme.
     * @return ColorDefinition []
     */
    public ColorDefinition[] getColors();

    /**
     * Returns the font overrides for this theme.
     * @return GradientDefinition []
     */
    public FontDefinition[] getFonts();

    /**
     * Returns the data map for this theme.
     *
     * @return the data map.  This will be read only.
     */
    public Map getData();
}
