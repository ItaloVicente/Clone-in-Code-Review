package org.eclipse.ui.tests.harness.util;

import java.util.Arrays;

import junit.framework.Assert;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

public final class ImageTests {

	private ImageTests() {
		super();
	}

    public static void assertEquals(Image i1, Image i2) {
        ImageData data1 = i1.getImageData();
        ImageData data2 = i2.getImageData();
        Assert.assertTrue(Arrays.equals(data1.data, data2.data));
    }

    public static void assertNotEquals(Image i1, Image i2) {
        ImageData data1 = i1.getImageData();
        ImageData data2 = i2.getImageData();
        Assert.assertFalse(Arrays.equals(data1.data, data2.data));
    }
}
