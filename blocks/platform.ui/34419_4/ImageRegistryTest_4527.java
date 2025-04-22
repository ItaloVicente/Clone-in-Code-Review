
package org.eclipse.jface.tests.images;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import junit.framework.TestCase;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.tests.TestPlugin;
import org.osgi.framework.Bundle;

public class FileImageDescriptorTest extends TestCase {

	protected static final String IMAGES_DIRECTORY = "/icons/imagetests";

	public FileImageDescriptorTest(String name) {
		super(name);
	}

	public void testFileImageDescriptorWorkbench() {

		Class missing = null;
		ArrayList images = new ArrayList();

		Bundle bundle = TestPlugin.getDefault().getBundle();
		Enumeration bundleEntries = bundle.getEntryPaths(IMAGES_DIRECTORY);

		while (bundleEntries.hasMoreElements()) {
			ImageDescriptor descriptor;
			String localImagePath = (String) bundleEntries.nextElement();
			URL[] files = FileLocator.findEntries(bundle, new Path(
					localImagePath));

			for (URL file : files) {

				if (file.getPath().lastIndexOf('.') < 0) {
					continue;
				}

				try {
					descriptor = ImageDescriptor.createFromFile(missing,
							FileLocator.toFileURL(file).getFile());
				} catch (IOException e) {
					fail(e.getLocalizedMessage());
					continue;
				}

				Image image = descriptor.createImage();
				images.add(image);

			}

		}

		Iterator imageIterator = images.iterator();
		while (imageIterator.hasNext()) {
			((Image) imageIterator.next()).dispose();
		}

	}

	public void testFileImageDescriptorLocal() {

		ImageDescriptor descriptor = ImageDescriptor.createFromFile(
				FileImageDescriptorTest.class, "anything.gif");

		Image image = descriptor.createImage();
		assertTrue("Could not find image", image != null);
		image.dispose();

	}

	public void testFileImageDescriptorMissing() {

		ImageDescriptor descriptor = ImageDescriptor.createFromFile(
				FileImageDescriptorTest.class, "missing.gif");

		Image image = descriptor.createImage(false);
		assertTrue("Found an image but should be null", image == null);
	}

	public void testFileImageDescriptorMissingWithDefault() {

		ImageDescriptor descriptor = ImageDescriptor.createFromFile(
				FileImageDescriptorTest.class, "missing.gif");

		Image image = descriptor.createImage(true);
		assertTrue("Did not find default image", image != null);
	}

}
