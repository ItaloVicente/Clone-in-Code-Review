package org.eclipse.egit.ui.performance;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.eclipse.egit.ui.performance.LocalRepositoryTestCase.PROJ1;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

public class SynchronizeWithWorkingTreeChangesPerformanceTest extends
		AbstractSynchronizeViewPerformanceTest {

	public SynchronizeWithWorkingTreeChangesPerformanceTest() {
		super(true);
	}

	protected void fillRepository() throws Exception {
		IProject project = getWorkspace().getRoot().getProject(PROJ1);
		int newFiles = 300;
		for (int i = 0; i < newFiles; i++) {
			String name = "n" + i;
			IFile newFile = project.getFile(name);
			byte[] content = ("x " + i).getBytes();
			newFile.create(new ByteArrayInputStream(content), true, null);
		}
	}

}
