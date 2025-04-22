package org.eclipse.jface.tests.images;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.jface.resource.ColorDescriptor;
import org.eclipse.jface.resource.DeviceResourceDescriptor;
import org.eclipse.jface.resource.DeviceResourceException;
import org.eclipse.jface.resource.DeviceResourceManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class ResourceManagerTest extends TestCase {
    
    private DeviceResourceDescriptor[] descriptors;
    private Image testImage;
    private Image testImage2;
    private Color testColor;
    private Color testColor2;
    private int numDupes;
    
    private DeviceResourceManager globalResourceManager;
    
    private static ImageDescriptor getImage(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui.tests", path);
    }
    
    private static final class TestDescriptor extends DeviceResourceDescriptor {
        DeviceResourceDescriptor toWrap;
        public static int refCount = 0;
        
        public TestDescriptor(DeviceResourceDescriptor toWrap) {
            this.toWrap = toWrap;
        }
        
        @Override
		public Object createResource(Device device) throws DeviceResourceException {
            Object result = toWrap.createResource(device);
            refCount++;
            return result;
        }
        @Override
		public void destroyResource(Object previouslyCreatedObject) {
            refCount--;
            toWrap.destroyResource(previouslyCreatedObject);
        }
        
        @Override
		public boolean equals(Object arg0) {
            if (arg0 instanceof TestDescriptor) {
                TestDescriptor td = (TestDescriptor)arg0;
                
                return td.toWrap.equals(toWrap);
            }
            
            return false;
        }
        
        @Override
		public int hashCode() {
            return toWrap.hashCode();
        }
    }
    
    @Override
	protected void setUp() throws Exception {
        super.setUp();
        TestDescriptor.refCount = 0;
        Display display = Display.getCurrent();
        globalResourceManager = new DeviceResourceManager(display);
        testImage = getImage("icons/anything.gif").createImage(display);
        testImage2 = getImage("icons/binary_co.gif").createImage(display);
        testColor = new Color(display, new RGB(10, 40, 20));
        testColor2 = new Color(display, new RGB(230, 100, 26));
        
        descriptors = new DeviceResourceDescriptor[] {
                new TestDescriptor(getImage("icons/anything.gif")),
                new TestDescriptor(getImage("icons/anything.gif")),
                new TestDescriptor(getImage("icons/binary_co.gif")),
                new TestDescriptor(getImage("icons/binary_co.gif")),
                new TestDescriptor(getImage("icons/mockeditorpart1.gif")),
                
                new TestDescriptor(getImage("icons/view.gif")), // 5
                new TestDescriptor(ImageDescriptor.createFromImage(testImage2)),
                new TestDescriptor(ImageDescriptor.createFromImage(testImage)),
                new TestDescriptor(ImageDescriptor.createFromImage(testImage)),
                new TestDescriptor(ImageDescriptor.createFromImage(testImage, display)),
                
                new TestDescriptor(ImageDescriptor.createFromImage(testImage, display)),  // 10
                new TestDescriptor(ImageDescriptor.createFromImage(testImage2, display)),
                new TestDescriptor(ColorDescriptor.createFrom(new RGB(10, 200, 54))),
                new TestDescriptor(ColorDescriptor.createFrom(new RGB(10, 200, 54))),
                new TestDescriptor(ColorDescriptor.createFrom(new RGB(200, 220, 54))),
                
                new TestDescriptor(ColorDescriptor.createFrom(testColor)), // 15
                new TestDescriptor(ColorDescriptor.createFrom(testColor)),
                new TestDescriptor(ColorDescriptor.createFrom(testColor2)),
                new TestDescriptor(ColorDescriptor.createFrom(testColor, display)),
                new TestDescriptor(ColorDescriptor.createFrom(testColor, display)),
                new TestDescriptor(ColorDescriptor.createFrom(testColor2, display))
                };
        
        numDupes = 11;
    }
    
    @Override
	protected void tearDown() throws Exception {
        super.tearDown();
        globalResourceManager.dispose();
        Assert.assertEquals("Detected leaks", 0, TestDescriptor.refCount);
        testImage.dispose();
        testImage2.dispose();
    }
    
    protected void validateResource(Object resource) throws Exception {
        Assert.assertNotNull("Allocated resource was null", resource);
        if (resource instanceof Image) {
            Image image = (Image) resource;
            
            Assert.assertTrue("Image is disposed", !image.isDisposed());
            return;
        }
    }
    
    public void testDescriptorAllocations() throws Exception {
        Display display = Display.getCurrent();
        
        Object[] resources = new Object[descriptors.length];
        
        for (int i = 0; i < descriptors.length; i++) {
            DeviceResourceDescriptor next = descriptors[i];
            
            Object resource = next.createResource(display);
            validateResource(resource);
            resources[i] = resource;
        }
        
        
        Assert.assertEquals("Expecting one resource to be allocated per descriptor", 
                descriptors.length, TestDescriptor.refCount);
        
        for (int i = 0; i < descriptors.length; i++) {
            DeviceResourceDescriptor next = descriptors[i];
            
            next.destroyResource(resources[i]);
        }   
    }
    
    public void testDeviceManagerAllocations() throws Exception {
        
        Object[] resources = new Object[descriptors.length];
        
        for (int i = 0; i < descriptors.length; i++) {
            DeviceResourceDescriptor next = descriptors[i];
            
            Object resource = globalResourceManager.create(next);
            validateResource(resource);
            resources[i] = resource;
        }
        
        Assert.assertEquals("Descriptors created from Images should not reallocate Images when the original can be reused",
                testImage, resources[9]);
        
        
        Assert.assertEquals("Duplicate descriptors should be reused", 
                descriptors.length - numDupes, 
                TestDescriptor.refCount);
        
        for (int i = 0; i < descriptors.length; i++) {
            DeviceResourceDescriptor next = descriptors[i];
            
            globalResourceManager.destroy(next);
        }
    }
    
    private void allocateResources(ResourceManager mgr, int[] toAllocate) throws Exception {
        for (int i = 0; i < toAllocate.length; i++) {
            int j = toAllocate[i];
            
            validateResource(mgr.create(descriptors[j]));
        }
    }
    
    private void deallocateResources(ResourceManager mgr, int[] toDeallocate) {
        for (int i = 0; i < toDeallocate.length; i++) {
            int j = toDeallocate[i];
            
            mgr.destroy(descriptors[j]);
        }
    }
    
    public void testLocalManagerAllocations() throws Exception {
        int[] gResources = {0, 1, 5, 3, 7, 12, 13, 14};
        int[] lm1Resources = {0, 2, 3, 4, 11, 12, 13, 15};
        int[] lm2Resources = {0, 1, 6, 7, 8, 12, 14, 16};
        
        LocalResourceManager lm1 = new LocalResourceManager(globalResourceManager);
        LocalResourceManager lm2 = new LocalResourceManager(globalResourceManager);
        
        allocateResources(globalResourceManager, gResources);
        
        int initialCount = TestDescriptor.refCount;
        
        allocateResources(lm1, lm1Resources);
        
        int lm1Count = TestDescriptor.refCount;
        
        allocateResources(lm2, lm2Resources);
        deallocateResources(lm2, lm2Resources);
        Assert.assertEquals(lm1Count, TestDescriptor.refCount);

        lm2.dispose();
        Assert.assertEquals(lm1Count, TestDescriptor.refCount);
        
        lm1.dispose();
        Assert.assertEquals(initialCount, TestDescriptor.refCount);
        
    }
    
    public void testResourceManagerFind() throws Exception {
    	DeviceResourceDescriptor descriptor = descriptors[0];
    	Object resource = globalResourceManager.find(descriptor);
    	assertNull("Resource should be null since it is not allocated", resource);
    	resource = globalResourceManager.create(descriptor);
    	
        validateResource(resource);
        
        Object foundResource = globalResourceManager.find(descriptor);
    	assertNotNull("Found resource should not be null", foundResource);
    	assertTrue("Found resource should be an image", foundResource instanceof Image);
    	
    	globalResourceManager.destroy(descriptor);
    }
}
