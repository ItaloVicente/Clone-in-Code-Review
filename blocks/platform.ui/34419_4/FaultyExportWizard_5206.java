package org.eclipse.ui.tests.session;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipFile;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.tests.harness.FileSystemHelper;
import org.eclipse.core.tests.session.SessionTestSuite;
import org.eclipse.core.tests.session.Setup;
import org.eclipse.core.tests.session.SetupManager;
import org.eclipse.core.tests.session.SetupManager.SetupException;
import org.eclipse.jface.util.Util;
import org.eclipse.osgi.service.environment.Constants;
import org.eclipse.ui.tests.TestPlugin;
import org.eclipse.ui.tests.harness.util.FileTool;

public class WorkbenchSessionTest extends SessionTestSuite {

	private Map arguments;

	private String dataLocation;

	public WorkbenchSessionTest(String dataLocation, Class clazz, Map arguments) {
		this(dataLocation, clazz);
		this.arguments = arguments;
	}

	public WorkbenchSessionTest(String dataLocation, Map arguments) {
		this(dataLocation);
		this.arguments = arguments;
	}

	public WorkbenchSessionTest(String dataLocation, Class clazz) {
		super("org.eclipse.ui.tests", clazz);
		setApplicationId(SessionTestSuite.UI_TEST_APPLICATION);
		this.dataLocation = dataLocation;
	}

	public WorkbenchSessionTest(String dataLocation) {
		super("org.eclipse.ui.tests");
		setApplicationId(SessionTestSuite.UI_TEST_APPLICATION);
		this.dataLocation = dataLocation;
	}

	@Override
	protected Setup newSetup() throws SetupException {
		Setup base = super.newSetup();
		try {
			base.setEclipseArgument(Setup.DATA, copyDataLocation());
			if (arguments != null) {
				for(Iterator i = arguments.entrySet().iterator(); i.hasNext(); ) {
					Map.Entry entry = (Map.Entry) i.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					base.setEclipseArgument(key, value);
				}
			}

			if (Util.isCocoa()) {
				String arch = System.getProperty("osgi.arch");
				if (Constants.ARCH_X86.equals(arch)) {
					Map vmArguments = new HashMap(1);
					vmArguments.put("d32", null);
					base.setVMArguments(vmArguments);
				}
			}
		} catch (Exception e) {
			throw SetupManager.getInstance().new SetupException(e.getMessage(),
					e);
		}
		return base;
	}

	private String copyDataLocation() throws IOException {
        TestPlugin plugin = TestPlugin.getDefault();
        if (plugin == null) {
			throw new IllegalStateException(
                    "TestPlugin default reference is null");
		}

        URL fullPathString = plugin.getDescriptor().find(
				new Path("data/workspaces/" + dataLocation + ".zip"));

        if (fullPathString == null) {
			throw new IllegalArgumentException();
		}

        IPath path = new Path(fullPathString.getPath());

        File origin = path.toFile();
        if (!origin.exists()) {
			throw new IllegalArgumentException();
		}

        ZipFile zFile = new ZipFile(origin);

		File destination = new File(FileSystemHelper.getRandomLocation(FileSystemHelper.getTempDir()).toOSString());
		FileTool.unzip(zFile, destination);
		return destination.getAbsolutePath();
	}
}
