
package org.eclipse.ui.tests.performance;

import junit.framework.Test;
import junit.framework.TestSuite;

public class EditorPerformanceSuite extends TestSuite {
    
    public static final String [] EDITOR_FILE_EXTENSIONS = {"perf_basic", "perf_outline", "java"};
    public static final String [][] EDITOR_SWITCH_PAIRS = {
        {"perf_outline", "java"},
        {"perf_basic", "perf_outline"}};
    
    public static Test suite() {
        return new EditorPerformanceSuite();
    }

    public EditorPerformanceSuite() {
        addOpenCloseScenarios();
        addSwitchScenarios();
        addOpenMultipleScenarios(true);
        addOpenMultipleScenarios(false);
    }


    private void addSwitchScenarios() {
        for (int i = 0; i < EDITOR_SWITCH_PAIRS.length; i++) {
            addTest(new EditorSwitchTest(EDITOR_SWITCH_PAIRS[i]));            
        }       
    }

    private void addOpenMultipleScenarios(boolean closeAll) {
        for (int i = 0; i < EDITOR_FILE_EXTENSIONS.length; i++) {
            addTest(new OpenMultipleEditorTest(EDITOR_FILE_EXTENSIONS[i], closeAll, BasicPerformanceTest.NONE));            
        }
    }

    private void addOpenCloseScenarios() {
        for (int i = 0; i < EDITOR_FILE_EXTENSIONS.length; i++) {
            addTest(new OpenCloseEditorTest(EDITOR_FILE_EXTENSIONS[i], i == 3 ? BasicPerformanceTest.LOCAL : BasicPerformanceTest.NONE));            
        }        
    }
}
