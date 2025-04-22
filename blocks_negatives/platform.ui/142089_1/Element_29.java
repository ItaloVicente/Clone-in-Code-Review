 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.tests.views.properties.tabbed.model;

import org.eclipse.swt.graphics.Image;

public abstract class Element {

    private String name;

    public Element(String aName) {
        super();
        this.name = aName;
    }

    public String getName() {
        return name;
    }

    public abstract Image getImage();
}
