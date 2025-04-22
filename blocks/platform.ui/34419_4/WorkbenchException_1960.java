package org.eclipse.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.registry.RegistryReader;

public class WorkbenchEncoding {

	private static Method CharsetIsSupportedMethod = null; 
	
	static {
		try {
			Class charsetClass = Class.forName("java.nio.charset.Charset"); //$NON-NLS-1$
			CharsetIsSupportedMethod = charsetClass.getMethod("isSupported", new Class[] { String.class }); //$NON-NLS-1$
		}
		catch (Exception e) {
		}
			
	}
	
	private static class EncodingsRegistryReader extends RegistryReader {
		
		private List encodings;
		
		public EncodingsRegistryReader(List definedEncodings) {
			super();
			encodings = definedEncodings;
		}

		@Override
		protected boolean readElement(IConfigurationElement element) {
			String name = element.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
			if (name != null) {
				encodings.add(name);
			}
			return true;
		}
	}

	public static String getWorkbenchDefaultEncoding() {
		return System.getProperty("file.encoding", "UTF-8");//$NON-NLS-1$ //$NON-NLS-2$
	}

	public static List getDefinedEncodings() {
		List definedEncodings = Collections.synchronizedList(new ArrayList());
		EncodingsRegistryReader reader = new EncodingsRegistryReader(definedEncodings);

		reader.readRegistry(Platform.getExtensionRegistry(), PlatformUI.PLUGIN_ID,
				IWorkbenchRegistryConstants.PL_ENCODINGS);

		String[] encodings = new String[definedEncodings.size()];
		List invalid = new ArrayList();
		definedEncodings.toArray(encodings);
		for (int i = 0; i < encodings.length; i++) {
			if (!isSupported(encodings[i])) {
				invalid.add(encodings[i]);
			}
		}

		Iterator invalidIterator = invalid.iterator();
		while (invalidIterator.hasNext()) {
			String next = (String) invalidIterator.next();
			WorkbenchPlugin.log(NLS.bind(WorkbenchMessages.WorkbenchEncoding_invalidCharset,  next ));
			definedEncodings.remove(next);

		}

		return definedEncodings;
	}

	private static boolean isSupported(String encoding) {
		if (CharsetIsSupportedMethod == null) {
			return true;
		}
		try {
			Object o = CharsetIsSupportedMethod.invoke(null, new Object[] { encoding });
			return Boolean.TRUE.equals(o);
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
			return false;
		}
		return true;
	}
}
