package org.eclipse.jface.tests.databinding.scenarios;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class BindingScenariosTestSuite extends TestSuite {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    private static Display display;

    private static Shell shell;

    public static Test suite() {
        return new TestSetup(new BindingScenariosTestSuite()) {
            @Override
			protected void setUp() throws Exception {
                Display d = Display.getDefault();
                shell = new Shell(d, SWT.SHELL_TRIM);
                shell.setLayout(new FillLayout());
            }

            @Override
			protected void tearDown() throws Exception {
                shell.close();
                shell.dispose();
                if (display != null) {
                    display.dispose();
                }
            }
        };
    }

    public BindingScenariosTestSuite() {
        addTestSuite(ButtonControlScenario.class);
        addTestSuite(ComboScenarios.class);
        addTestSuite(ComboUpdatingTest.class);
        addTestSuite(ComboViewerScenario.class);
        addTestSuite(CustomConverterScenarios.class);
        addTestSuite(CustomScenarios.class);
        addTestSuite(ListViewerScenario.class);
        addTestSuite(MasterDetailScenarios.class);
        addTestSuite(NewTableScenarios.class);
        addTestSuite(NPETestScenario.class);
        addTestSuite(PropertyScenarios.class);
        addTestSuite(SpinnerControlScenario.class);
        addTestSuite(TableScenarios.class);
        addTestSuite(TextControlScenario.class);
    }

    public static Shell getShell() {
        return shell;
    }

}
