package org.eclipse.ui.tests.decorators;

import junit.framework.TestSuite;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.decorators.DecorationResult;
import org.eclipse.ui.internal.decorators.DecoratorManager;
import org.eclipse.ui.internal.decorators.LightweightDecoratorManager;
import org.eclipse.ui.tests.harness.util.UITestCase;
import org.eclipse.ui.tests.menus.ObjectContributionClasses;
import org.eclipse.ui.tests.menus.ObjectContributionClasses.ICommon;

public class DecoratorAdaptableTests extends UITestCase {

	public static TestSuite suite() {
		TestSuite ts = new TestSuite();
		ts.addTest(new DecoratorAdaptableTests("testAdaptables"));
		ts.addTest(new DecoratorAdaptableTests("testNonAdaptableContributions"));
		ts.addTest(new DecoratorAdaptableTests("testContributorResourceAdapter"));
		return ts;
	}

    public DecoratorAdaptableTests(String testName) {
        super(testName);
    }

    private DecoratorManager getDecoratorManager() {
        return WorkbenchPlugin.getDefault().getDecoratorManager();
    }

    private String getDecorationTextFor(Object object) {
        DecoratorManager dm = getDecoratorManager();
        LightweightDecoratorManager ldm = dm.getLightweightManager();
        DecorationResult result = ldm.getDecorationResult(object);
        return result.decorateWithText("Default label");
    }

	private void assertDecorated(String testSubName, String[] expectedSuffixes,
			Object[] elements, Class adaptedClass, boolean shouldHaveMatches) {
        for (Object object : elements) {
            String text = getDecorationTextFor(object);
            boolean allMatchesFound = true;
            for (String suffix : expectedSuffixes) {
                if (text.indexOf(suffix) == -1) {
                    allMatchesFound = false;
                }
            }
            assertTrue("Adaptable test " + testSubName + " has failed for object " + object.toString(), allMatchesFound == shouldHaveMatches);
        }

    }

    @Override
	protected void doSetUp() throws Exception {
        PlatformUI.getWorkbench().getDecoratorManager().setEnabled(TestAdaptableDecoratorContributor.ID, true);
        PlatformUI.getWorkbench().getDecoratorManager().setEnabled(TestUnadaptableDecoratorContributor.ID, true);
        PlatformUI.getWorkbench().getDecoratorManager().setEnabled(TestResourceDecoratorContributor.ID, true);
        PlatformUI.getWorkbench().getDecoratorManager().setEnabled(TestResourceMappingDecoratorContributor.ID, true);
        super.doSetUp();
    }

    @Override
	protected void doTearDown() throws Exception {
        PlatformUI.getWorkbench().getDecoratorManager().setEnabled(TestAdaptableDecoratorContributor.ID, false);
        PlatformUI.getWorkbench().getDecoratorManager().setEnabled(TestUnadaptableDecoratorContributor.ID, false);
        PlatformUI.getWorkbench().getDecoratorManager().setEnabled(TestResourceDecoratorContributor.ID, false);
        PlatformUI.getWorkbench().getDecoratorManager().setEnabled(TestResourceMappingDecoratorContributor.ID, false);
        super.doTearDown();
    }

	public final void testAdaptables() {
        assertDecorated("1",
                new String[] {TestAdaptableDecoratorContributor.SUFFIX},
                new Object[] {
                        new ObjectContributionClasses.Common(),
                        new ObjectContributionClasses.C(),
                        new ObjectContributionClasses.B(),
                        new ObjectContributionClasses.A()
                },
                ICommon.class,
                true
            );
        assertDecorated("2",
                new String[] {TestAdaptableDecoratorContributor.SUFFIX},
                new Object[] {
                        new Object()
                },
                ICommon.class,
                false
            );
    }

	public final void testNonAdaptableContributions() {
        assertDecorated("1",
                new String[] {TestUnadaptableDecoratorContributor.SUFFIX},
                new Object[] {
                        new ObjectContributionClasses.A(),
                        new ObjectContributionClasses.B()},
                ICommon.class,
                false
            );
        assertDecorated("2",
                new String[] {TestUnadaptableDecoratorContributor.SUFFIX},
                new Object[] {
                        new ObjectContributionClasses.D(),
                        new ObjectContributionClasses.C(),
                        new ObjectContributionClasses.Common()},
                ICommon.class,
                true
            );
    }

    public final void testContributorResourceAdapter() throws CoreException {

        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IProject testProject = workspace.getRoot().getProject(ObjectContributionClasses.PROJECT_NAME);
        if(! testProject.exists()) {
            testProject.create(null);
        }
        if(! testProject.isOpen()) {
            testProject.open(null);
        }

        assertDecorated("1",
                new String[] {"IResource.1"},
                new Object[] {
                    new ObjectContributionClasses.CResource(),
                    new ObjectContributionClasses.CFile()},
                IResource.class,
                true
            );

        assertDecorated("2",
                new String[] {"ResourceMapping.1"},
                new Object[] {
                        new ObjectContributionClasses.CFile(),
                        new ObjectContributionClasses.CResource()},
                ResourceMapping.class,
                true
            );
        assertDecorated("3",
                new String[] {"ResourceMapping.1", "IResource.1"},
                new Object[] {
                    new ObjectContributionClasses.ModelElement()},
                ResourceMapping.class,
                true
            );
        assertDecorated("4",
                new String[] {"ResourceMapping.1", "IResource.1"},
                new Object[] {
                    new ObjectContributionClasses.CResourceOnly()},
                ResourceMapping.class,
                true
            );
    }
}
