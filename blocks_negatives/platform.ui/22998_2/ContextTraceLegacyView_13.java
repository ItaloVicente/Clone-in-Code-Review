/*******************************************************************************
 * Copyright (c) 2010, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.e4.core.internal.contexts.debug.ui.e4;

import java.util.List;
import javax.inject.Inject;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.descriptor.basic.MPartDescriptor;
import org.eclipse.e4.ui.model.application.descriptor.basic.impl.BasicFactoryImpl;
import org.eclipse.e4.ui.workbench.swt.internal.copy.WorkbenchSWTMessages;

/* To use e4-style view contribtuion, add to plugin.xml :
   <extension
         id="ContextDebug"
         name="Context Trace and Debug"
         point="org.eclipse.e4.workbench.model">
      <processor
            beforefragment="true"
            class="org.eclipse.e4.core.internal.contexts.debug.ui.e4.ContextsDebugProcessor">
      </processor>
   </extension>
 */

public class ContextsDebugProcessor {


	@Inject
	public ContextsDebugProcessor() {
	}

	@Execute
	public void addDebugDescriptor(MApplication application) {
		List<MPartDescriptor> descriptors = application.getDescriptors();
		for (MPartDescriptor desc : descriptors) {
			if (DESCRIPTOR_ID.equals(desc.getElementId()))
		}

		MPartDescriptor descriptor = BasicFactoryImpl.eINSTANCE.createPartDescriptor();
		descriptor.setElementId(DESCRIPTOR_ID);

		List<String> tags = descriptor.getTags();

		descriptor.setCloseable(true);
		descriptor.setAllowMultiple(false);
		descriptor.setIconURI("platform:/plugin/org.eclipse.e4.core.contexts.debug/icons/full/obj16/contexts.gif");

		descriptors.add(descriptor);
	}

}
