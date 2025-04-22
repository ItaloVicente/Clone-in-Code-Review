/*
 * Copyright (C) 2021 Thomas Wolf <thomas.wolf@paranor.ch> and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package org.eclipse.jgit.gpg.bc;

import org.eclipse.jgit.gpg.bc.internal.BouncyCastleGpgSigner;
import org.eclipse.jgit.lib.GpgSigner;

/**
 * Factory for creating a {@link GpgSigner} based on Bouncy Castle.
 *
 * @since 5.11
 */
public final class BouncyCastleGpgSignerFactory {

	private BouncyCastleGpgSignerFactory() {
	}

	/**
	 * Creates a new {@link GpgSigner}.
	 *
	 * @return the {@link GpgSigner}
	 */
	public static GpgSigner create() {
		return new BouncyCastleGpgSigner();
	}
}
