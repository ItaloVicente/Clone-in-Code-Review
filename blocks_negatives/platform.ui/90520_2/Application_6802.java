/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.examples.propertysheet;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.texteditor.IDocumentProvider;

/**
 * This class is an example of the implementation of a simple parser.
 */
public class UserFileParser {
    /**
     * Return the fabricated result for this example.
     *
     */
    private static IAdaptable getFabricatedResult() {
        GroupElement root = new GroupElement(
                MessageUtil.getString("Everybody"), null); //$NON-NLS-1$
        GroupElement userGroup = root.createSubGroup(MessageUtil
        GroupElement ottGroup = userGroup.createSubGroup(MessageUtil
        GroupElement uiTeam = ottGroup.createSubGroup(MessageUtil
        user1.setEmailAddress(new EmailAddress(MessageUtil
        user1
                .setAddress(new Address(
                        new StreetAddress(232, MessageUtil
                                .getString("Champlain")), MessageUtil.getString("Hull"), Integer.valueOf(5), MessageUtil.getString("A1B2C3"))); //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$
        user1.setBirthday(new Birthday(18, 1, 1981));
        user1.setCoop(Boolean.TRUE);
        user1.setHairColor(new RGB(0, 0, 0));
        user1.setEyeColor(new RGB(0, 0, 0));
        user2.setEmailAddress(new EmailAddress(MessageUtil
        user2
                .setAddress(new Address(
                        new StreetAddress(),
                        MessageUtil.getString("Toronto"), Integer.valueOf(4), MessageUtil.getString("A1B2C3"))); //$NON-NLS-2$ //$NON-NLS-1$
        user2.setBirthday(new Birthday(7, 5, 1978));
        user2.setCoop(Boolean.TRUE);
        user2.setHairColor(new RGB(0, 0, 0));
        user2.setEyeColor(new RGB(0, 0, 0));

        user3.setEmailAddress(new EmailAddress(MessageUtil
        user3
                .setAddress(new Address(
                        new StreetAddress(),
                        MessageUtil.getString("Ottawa"), Integer.valueOf(4), MessageUtil.getString("A1B2C3"))); //$NON-NLS-2$ //$NON-NLS-1$
        user3.setBirthday(new Birthday(11, 23, 1962));
        user3.setHairColor(new RGB(0, 0, 0));
        user3.setEyeColor(new RGB(0, 0, 0));

        user4.setEmailAddress(new EmailAddress(MessageUtil
        user4
                .setAddress(new Address(
                        new StreetAddress(),
                        MessageUtil.getString("Ottawa"), Integer.valueOf(4), MessageUtil.getString("A1B2C3"))); //$NON-NLS-2$ //$NON-NLS-1$
        user5.setEmailAddress(new EmailAddress(MessageUtil
        user5
                .setAddress(new Address(
                        new StreetAddress(),
                        MessageUtil.getString("Ottawa"), Integer.valueOf(4), MessageUtil.getString("A1B2C3"))); //$NON-NLS-2$ //$NON-NLS-1$
        user6.setEmailAddress(new EmailAddress(MessageUtil
        user6
                .setAddress(new Address(
                        new StreetAddress(),
                        MessageUtil.getString("Ottawa"), Integer.valueOf(4), MessageUtil.getString("A1B2C3"))); //$NON-NLS-2$ //$NON-NLS-1$
        greg.setEmailAddress(new EmailAddress(MessageUtil
        greg
                .setAddress(new Address(
                        new StreetAddress(),
                        MessageUtil.getString("Ottawa"), Integer.valueOf(4), MessageUtil.getString("A1B2C3"))); //$NON-NLS-2$ //$NON-NLS-1$

        return root;
    }

    /**
     * Parse the input given by the argument. For this example we do no parsing and return
     * a fabricated result.
     *
     */
    public IAdaptable parse(IDocumentProvider documentProvider) {

        return getFabricatedResult();
    }
}
