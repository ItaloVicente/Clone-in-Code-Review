/*
 * Copyright (C) 2020, Thomas Wolf <thomas.wolf@paranor.ch> and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package org.eclipse.jgit.internal.transport.sshd;

import java.util.concurrent.CancellationException;

/**
 * An exception to report that the user canceled the SSH authentication.
 */
public class AuthenticationCanceledException extends CancellationException {


	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@link AuthenticationCanceledException}.
	 */
	public AuthenticationCanceledException() {
		super(SshdText.get().authenticationCanceled);
	}
}
