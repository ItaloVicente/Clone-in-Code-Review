 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.tests.views.properties.tabbed.model;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class Error
    extends Element {

    public Error(String name) {
        super(name);
    }

    @Override
	public Image getImage() {
        return PlatformUI.getWorkbench().getSharedImages().getImage(
            ISharedImages.IMG_OBJS_ERROR_TSK);
    }

}
