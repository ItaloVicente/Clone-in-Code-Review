/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.ide;

/**
 * Identifiers for IDE menus, toolbars and groups.
 * <p>
 * This interface contains constants only; it is not intended to be implemented
 * or extended.
 * </p>
 *
 * Note: want to move IDE-specific stuff out of IWorkbenchActionConstants.
 *   There's still some cleanup to be done here (and there).
 *
 * @since 3.0
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IIDEActionConstants {

    /**
     * Name of standard File menu (value <code>"file"</code>).
     */

    /**
     * Name of standard Edit menu (value <code>"edit"</code>).
     */

    /**
     * Name of standard Navigate menu (value <code>"navigate"</code>).
     */

    /**
     * Name of standard Project menu (value <code>"project"</code>).
     */

    /**
     * Name of standard Window menu (value <code>"window"</code>).
     */

    /**
     * Name of standard Help menu (value <code>"help"</code>).
     */

    /**
     * File menu: name of group for start of menu (value <code>"fileStart"</code>).
     */

    /**
     * File menu: name of group for end of menu (value <code>"fileEnd"</code>).
     */

    /**
     * File menu: name of group for extra New-like actions (value <code>"new.ext"</code>).
     */

    /**
     * File menu: name of group for extra Close-like actions (value <code>"close.ext"</code>).
     */

    /**
     * File menu: name of group for extra Save-like actions (value <code>"save.ext"</code>).
     */

    /**
     * File menu: name of group for extra Print-like actions (value <code>"print.ext"</code>).
     */

    /**
     * File menu: name of group for extra Import-like actions (value <code>"import.ext"</code>).
     */

    /**
     * File menu: name of "Most Recently Used File" group.
     * (value <code>"mru"</code>).
     */

    /**
     * Edit menu: name of group for start of menu (value <code>"editStart"</code>).
     */

    /**
     * Edit menu: name of group for end of menu (value <code>"editEnd"</code>).
     */

    /**
     * Edit menu: name of group for extra Undo-like actions (value <code>"undo.ext"</code>).
     */

    /**
     * Edit menu: name of group for extra Cut-like actions (value <code>"cut.ext"</code>).
     */

    /**
     * Edit menu: name of group for extra Find-like actions (value <code>"find.ext"</code>).
     * <p>Note: The value of this constant has changed in 3.3 to match the specification;
     * before 3.3, its value was incorrect (<code>"cut.ext"</code>).  See bug 155856 for details.</p>
     */

    /**
     * Edit menu: name of group for extra Add-like actions (value <code>"add.ext"</code>).
     */

    /**
     * Workbench menu: name of group for extra Build-like actions
     * (value <code>"build.ext"</code>).
     */

    /**
     * Workbench toolbar id for file toolbar group.
     *
     * @since 2.1
     */

    /**
     * Workbench toolbar id for navigate toolbar group.
     *
     * @since 2.1
     */


    /**
     * Group id for pin toolbar group.
     *
     * @since 2.1
     */

    /**
     * Group ids for history toolbar group.
     *
     * @since 2.1
     */

    /**
     * Group ids for new toolbar group.
     *
     * @since 2.1
     */

    /**
     * Group ids for save toolbar group.
     *
     * @since 2.1
     */

    /**
     * Group ids for build toolbar group.
     *
     * @since 2.1
     */

    /**
     * Pop-up menu: name of group for Add actions (value <code>"group.add"</code>).
     */

    /**
     * Pop-up menu and cool bar: name of group for File actions (value <code>"group.file"</code>).
     */

    /**
     * Coolbar: name of group for Navigate actions (value <code>"group.nav"</code>).
     */

    /**
     * Pop-up menu: name of group for Show In actions (value <code>"group.showIn"</code>).
     *
     * @since 2.1
     */

    /**
     * Navigate menu: name of group for start of menu
     * (value <code>"navStart"</code>).
     */

    /**
     * Navigate menu: name of group for end of menu
     * (value <code>"navEnd"</code>).
     */

    /**
     * Navigate menu: name of group for extra Open actions
     * (value <code>"open.ext"</code>).
     */

    /**
     * Navigate menu: name of group for extra Show actions
     * (value <code>"show.ext"</code>).
     */

    /**
     * Navigate menu: name of standard Go Into global action
     * (value <code>"goInto"</code>).
     */

    /**
     * Navigate menu: name of standard Go To submenu
     * (value <code>"goTo"</code>).
     */

    /**
     * Navigate menu: name of standard Go To Resource global action
     * (value <code>"goToResource"</code>).
     *
     * Note:should be in an action factory
     */

    /**
     * Project menu: name of group for start of menu
     * (value <code>"projStart"</code>).
     */

    /**
     * Project menu: name of group for start of menu
     * (value <code>"projEnd"</code>).
     */

    /**
     * Help menu: name of group for start of menu
     * (value <code>"helpStart"</code>).
     */

    /**
     * Help menu: name of group for end of menu
     * (value <code>"helpEnd"</code>).
     */

}

