package org.eclipse.ui.tests.datatransfer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.tests.harness.FileSystemHelper;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.internal.wizards.datatransfer.FileSystemExportOperation;
import org.eclipse.ui.tests.harness.util.FileUtil;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class ExportFileSystemOperationTest extends UITestCase implements
		IOverwriteQuery {

    private static final String[] directoryNames = { "dir1", "dir2" };

    private static final String[] fileNames = { "file1.txt", "file2.txt" };

    private String localDirectory;

    private IProject project;
    
	public ExportFileSystemOperationTest(String testName) {
		super(testName);
	}

	@Override
	public String queryOverwrite(String pathString) {
		return "";
	}
		
    @Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		project = FileUtil.createProject("Export" + getName());
		File destination = 
			new File(FileSystemHelper.getRandomLocation(FileSystemHelper.getTempDir())
    			.toOSString());
		localDirectory = destination.getAbsolutePath();
		if (!destination.mkdirs())
			fail("Could not set up destination directory for " + getName());
	    setUpData();		
	}

    private void setUpData(){
    	try{
	    	for(int i = 0; i < directoryNames.length; i++){
	    		IFolder folder = project.getFolder(directoryNames[i]);
	    		folder.create(false, true, new NullProgressMonitor());
	    		for (int k = 0; k < fileNames.length; k++){
	    			IFile file = folder.getFile(fileNames[k]);
	    			String contents =
	    				directoryNames[i] + ", " + fileNames[k];		
	    			file.create(new ByteArrayInputStream(contents.getBytes()), 
	    				true, new NullProgressMonitor());
	    		}
	    	}
    	}
    	catch(Exception e){
    		fail(e.toString());
    	}
    }
    
	@Override
	protected void doTearDown() throws Exception {
        super.doTearDown();
        File root = new File(localDirectory);
        if (root.exists()){
        	FileSystemHelper.clear(root);
        }
        try {
            project.delete(true, true, null);
        } catch (CoreException e) {
            fail(e.toString());
        }
        finally{
        	project = null;
        	localDirectory = null;
        }        
	}
	
	public void testGetStatus() throws Exception {
		List resources = new ArrayList();
		resources.add(project);
        FileSystemExportOperation operation = 
        	new FileSystemExportOperation(
        			null, resources, localDirectory, this);

        assertTrue(operation.getStatus().getCode() == IStatus.OK);
    }
	
	public void testExportRootResource() throws Exception {
		List resources = new ArrayList();
		resources.add(project);
        FileSystemExportOperation operation = 
        	new FileSystemExportOperation(
        			null, resources, localDirectory, this);
        openTestWindow().run(true, true, operation);
        
        verifyFolders(directoryNames.length);		
	}
	
	public void testExportResources() throws Exception {
		List resources = new ArrayList();
		IResource[] members = project.members();
		for (int i = 0; i < members.length; i++){
			resources.add(members[i]);
		}
        FileSystemExportOperation operation = 
        	new FileSystemExportOperation(
        			null, resources, localDirectory, this);
        openTestWindow().run(true, true, operation);
        
        verifyFolders(directoryNames.length);				
	}
	
	public void testExportFolderCreateDirectoryStructure() throws Exception {
		List resources = new ArrayList();
		IResource[] members = project.members();
		for (int i = 0; i < members.length; i++){
			if (isDirectory(members[i]))
				resources.add(members[i]);
		}
        FileSystemExportOperation operation = 
        	new FileSystemExportOperation(
        			null, resources, localDirectory, this);

        operation.setCreateContainerDirectories(true);
        operation.setCreateLeadupStructure(false);
        openTestWindow().run(true, true, operation);
        
        verifyFolders(directoryNames.length, false);		
	}
	
	public void testExportFilesCreateDirectoryStructure() throws Exception {
		List resources = new ArrayList();
		IResource[] members = project.members();
		for (int i = 0; i < members.length; i++){
			if (isDirectory(members[i])){
				IResource[] folderMembers = ((IFolder)members[i]).members();
				for (int k = 0; k < folderMembers.length; k++){
					if (isFile(folderMembers[k])){
						resources.add(folderMembers[k]);
					}
				}
			}
		}
		FileSystemExportOperation operation = 
        	new FileSystemExportOperation(
        			null, resources, localDirectory, this);

        operation.setCreateContainerDirectories(true);
        operation.setCreateLeadupStructure(false);
        openTestWindow().run(true, true, operation);
        
        verifyFiles(resources);			
	}
	
	public void testExportOverwrite() throws Exception {
		List resources = new ArrayList();
		resources.add(project);
        FileSystemExportOperation operation = 
        	new FileSystemExportOperation(
        			null, resources, localDirectory, this);
        openTestWindow().run(true, true, operation);
        operation.setOverwriteFiles(true);
        operation.setCreateContainerDirectories(false);
        operation.setCreateLeadupStructure(false);
        openTestWindow().run(true, true, operation);
        
        verifyFolders(directoryNames.length);		
	}	
	
	private boolean isFile(IResource resource){
		for (int i = 0; i < fileNames.length; i++){
			if (fileNames[i].equals(resource.getName()))
				return true;
		}
		return false;		
	}
	
	private void verifyFiles(List resources){
		for (int i = 0; i < resources.size(); i++){
			IResource resource = (IResource)resources.get(i);
			assertTrue(
				"Export should have exported " + resource.getName(),
				isFile(resource));
				
		}
	}
	
	private void verifyFolders(int folderCount){
		verifyFolders(folderCount, true);
	}
	
	private void verifyFolders(int folderCount, boolean includeRootFolder){
		File root; 
		if (includeRootFolder){
			root = new File(localDirectory, project.getName());
			assertTrue("Export failed: " + project.getName() + " folder does not exist", root.exists());
		}
		else{
			root = new File(localDirectory);
		}
        File[] files = root.listFiles();
        List directories = new ArrayList();
        if (files != null){	        
	        for (int i = 0; i < files.length; i++){
	        	if (files[i].isDirectory())
	        		directories.add(files[i]);
	        }
        }
        assertEquals("Export failed to Export all directories",
                folderCount, directories.size());
        
        for (int i = 0; i < directories.size(); i++) {
        	File directory = (File)directories.get(i);
            assertTrue("Export failed to export directory " + directory.getName(), directory.exists());
            verifyFolder(directory);
        }
	}
	
	private void verifyFolder(File directory){
    	File[] files = directory.listFiles();
    	if (files != null){
	    	for (int k = 0; k < files.length; k++){
	    		File file = files[k];
	    		assertTrue("Export failed to export file: " + file.getName(), file.exists());
	    	}
    	}
	}

	private boolean isDirectory(IResource resource){
		for (int i = 0; i < directoryNames.length; i++){
			if (directoryNames[i].equals(resource.getName()))
				return true;
		}
		return false;
	}
}
