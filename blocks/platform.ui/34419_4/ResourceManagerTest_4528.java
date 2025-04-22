package org.eclipse.jface.tests.images;

import junit.framework.TestCase;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IconAndMessageDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;

public class ImageRegistryTest extends TestCase {
    public ImageRegistryTest(String name) {
        super(name);
    }

    public static void main(String args[]) {
        junit.textui.TestRunner.run(ImageRegistryTest.class);
    }

    public void testGetNull() {
        ImageRegistry reg = JFaceResources.getImageRegistry();

        Image result = reg.get((String) null);
        assertTrue("Registry should handle null", result == null);
    }

    public void testGetString() {

        Dialog.getBlockedHandler();

        String[] imageNames = new String[] { Dialog.DLG_IMG_ERROR,
                Dialog.DLG_IMG_INFO, Dialog.DLG_IMG_QUESTION,
                Dialog.DLG_IMG_WARNING, Dialog.DLG_IMG_MESSAGE_ERROR,
                Dialog.DLG_IMG_MESSAGE_INFO, Dialog.DLG_IMG_MESSAGE_WARNING };

        ImageRegistry reg = JFaceResources.getImageRegistry();

        for (String imageName : imageNames) {
            Image image1 = reg.get(imageName);
            assertTrue("Returned null image", image1 != null);
        }

    }

    public void testGetIconMessageDialogImages() {

        IconAndMessageDialog iconDialog = new MessageDialog(null,
                "testGetDialogIcons", null, "Message", Window.CANCEL,
                new String[] { "cancel" }, 0);

        Image[] images = new Image[] { iconDialog.getErrorImage(),
                iconDialog.getInfoImage(), iconDialog.getQuestionImage(),
                iconDialog.getWarningImage() };

        for (Image image : images) {
            assertTrue("Returned null image", image != null);
        }

    }
}
