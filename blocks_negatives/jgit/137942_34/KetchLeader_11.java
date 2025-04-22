/*
 * Copyright (C) 2016, Google Inc. and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.eclipse.jgit.internal.ketch;

import org.eclipse.jgit.revwalk.FooterKey;

/**
 * Frequently used constants in a Ketch system.
 */
public class KetchConstants {
	/**
	 * Default reference namespace holding {@link #ACCEPTED} and
	 * {@link #COMMITTED} references and the {@link #STAGE} sub-namespace.
	 */

	/** Reference name holding the RefTree accepted by a follower. */

	/** Reference name holding the RefTree known to be committed. */

	/** Reference subdirectory holding proposed heads. */

	/** Footer containing the current term. */

	/** Section for Ketch configuration ({@code ketch}). */

	/** Behavior for a replica ({@code remote.$name.ketch-type}) */

	/** Behavior for a replica ({@code remote.$name.ketch-commit}) */

	/** Behavior for a replica ({@code remote.$name.ketch-speed}) */

	private KetchConstants() {
	}
}
