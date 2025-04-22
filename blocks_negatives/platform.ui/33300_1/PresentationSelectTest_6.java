/*******************************************************************************
 * Copyright (c) 2004, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.tests.performance.presentations;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

/**
 * @since 3.1
 */
public class PresentationPerformanceTestSuite extends TestSuite {

    
    /**
     * Returns the suite. This is required to use the JUnit Launcher.
     */
    public static Test suite() {
        return new PresentationPerformanceTestSuite();
    }
    
    /**
     * 
     */
    public PresentationPerformanceTestSuite() {        
        String[] ids = getPresentationIds();
        
        for (int i = 0; i < ids.length; i++) {
            String string = ids[i];
            
            addTests(string);
        }
    }

    private void addTests(String presentationId) {
        
    }
    
    private static String[] getPresentationIds() {
        return listIds(IWorkbenchRegistryConstants.PL_PRESENTATION_FACTORIES,
                "factory");
    }
    
    private static String[] listIds(String extensionPointId, String elementName) {
        
        List result = new ArrayList();
        
        IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
                .getExtensionPoint(WorkbenchPlugin.PI_WORKBENCH, extensionPointId);
        if (extensionPoint == null) {
            WorkbenchPlugin
            return null;
        }
        
        IConfigurationElement[] elements = extensionPoint
                .getConfigurationElements();
        for (int j = 0; j < elements.length; j++) {
            IConfigurationElement element = elements[j];
            if (elementName == null || elementName.equals(element.getName())) {
                if (strID != null) {
                    result.add(strID);
                }
            }
        }
        
        return (String[]) result.toArray(new String[result.size()]);
    }    
    
}
