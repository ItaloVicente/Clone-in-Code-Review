package org.eclipse.ui.tests.decorators;

import org.eclipse.core.resources.mapping.ResourceMapping;

public class TestResourceMappingDecoratorContributor extends
        TestAdaptableDecoratorContributor {
    public static final String SUFFIX = "ResourceMapping.1";
    public static final String ID = "org.eclipse.ui.tests.decorators.resourceMappingDescorator";
    public TestResourceMappingDecoratorContributor() {
        setExpectedElementType(ResourceMapping.class);
        setSuffix(SUFFIX);
    }
}
