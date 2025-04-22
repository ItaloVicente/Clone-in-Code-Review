package org.eclipse.ui.tests.decorators;

import org.eclipse.core.resources.IResource;

public class TestResourceDecoratorContributor extends TestAdaptableDecoratorContributor {
    public static final String SUFFIX = "IResource.1";
    public static final String ID = "org.eclipse.ui.tests.decorators.resourceDescorator";
    public TestResourceDecoratorContributor() {
        setExpectedElementType(IResource.class);
        setSuffix(SUFFIX);
    }
}
