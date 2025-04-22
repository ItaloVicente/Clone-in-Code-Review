/*******************************************************************************
 * Copyright (c) 2004, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.tests.performance;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @since 3.1
 */
public class EditorPerformanceSuite extends TestSuite {

    public static final String [] EDITOR_FILE_EXTENSIONS = {"perf_basic", "perf_outline", "java"};
    public static final String [][] EDITOR_SWITCH_PAIRS = {
        {"perf_outline", "java"},
        {"perf_basic", "perf_outline"}};

    /**
     * Returns the suite. This is required to use the JUnit Launcher.
     */
    public static Test suite() {
        return new EditorPerformanceSuite();
    }

    public EditorPerformanceSuite() {
        addOpenCloseScenarios();
        addSwitchScenarios();
        addOpenMultipleScenarios(true);
        addOpenMultipleScenarios(false);
    }


    /**
     *
     */
    private void addSwitchScenarios() {
        for (int i = 0; i < EDITOR_SWITCH_PAIRS.length; i++) {
            addTest(new EditorSwitchTest(EDITOR_SWITCH_PAIRS[i]));
        }
    }

    /**
     *
     */
    private void addOpenMultipleScenarios(boolean closeAll) {
        for (int i = 0; i < EDITOR_FILE_EXTENSIONS.length; i++) {
            addTest(new OpenMultipleEditorTest(EDITOR_FILE_EXTENSIONS[i], closeAll, BasicPerformanceTest.NONE));
        }
    }

    /**
     *
     */
    private void addOpenCloseScenarios() {
        for (int i = 0; i < EDITOR_FILE_EXTENSIONS.length; i++) {
            addTest(new OpenCloseEditorTest(EDITOR_FILE_EXTENSIONS[i], i == 3 ? BasicPerformanceTest.LOCAL : BasicPerformanceTest.NONE));
        }
    }
}
