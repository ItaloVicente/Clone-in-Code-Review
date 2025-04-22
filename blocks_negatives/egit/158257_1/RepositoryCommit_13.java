/*******************************************************************************
 * Copyright (C) 2016, Thomas Wolf <thomas.wolf@paranor.ch>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.egit.ui.internal.commit;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

/**
 * A {@link ContentOutlinePage} that is to be nested in a
 * {@link MultiPageEditorContentOutlinePage}.
 */
public class NestedContentOutlinePage extends ContentOutlinePage {

	@Override
	public void init(IPageSite pageSite) {
		ISelectionProvider provider = pageSite.getSelectionProvider();
		super.init(pageSite);
		pageSite.setSelectionProvider(provider);
	}
}
