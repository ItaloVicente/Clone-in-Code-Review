package org.eclipse.jface.tests.viewers;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

public class CheckboxTreeViewerMissingTest extends CheckboxTreeViewerTest {
    private static Image testImage;

    public static Image getMissingImage() {
        if (testImage == null)
            testImage = ImageDescriptor.createFromFile(TestLabelProvider.class,
                    "images/missing.gif").createImage();
        return testImage;
    }

    public static class CheckboxMissingTableTestLabelProvider extends
            CheckboxTreeViewerTest.CheckboxTableTestLabelProvider implements
            ITableLabelProvider {
        @Override
		public Image getImage(Object element) {
            return getMissingImage();
        }

    }

    public CheckboxTreeViewerMissingTest(String name) {
        super(name);
    }

    @Override
	public IBaseLabelProvider getTestLabelProvider() {
        return new CheckboxMissingTableTestLabelProvider();
    }

    @Override
	public void tearDown() {
        super.tearDown();
        if (testImage != null) {
            testImage.dispose();
            testImage = null;
        }
    }

    @Override
	public void testLabelProvider() {
        super.testLabelProvider();
    }

}
