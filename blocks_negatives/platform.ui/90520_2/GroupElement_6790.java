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

import org.eclipse.jface.viewers.ICellEditorValidator;

/**
 * Validator for email addresses
 */
public class EmailAddressValidator implements ICellEditorValidator {
    /**
     * The <code>EmailAddressValidator</code> implementation of this
     * <code>ICellEditorValidator</code> method
     * determines if the value is a valid email address.
     * (check to see if it is non-null and contains an @)
     */
    @Override
	public String isValid(Object value) {
        if (value == null) {
        }
        String emailAddress = (String) value;
        int index = emailAddress.indexOf('@');
        int length = emailAddress.length();
        if (index > 0 && index < length) {
            return null;
        }
        return MessageUtil
    }
}
