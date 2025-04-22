/*******************************************************************************
 * Copyright (C) 2021 Thomas Wolf <thomas.wolf@paranor.ch>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.egit.internal.mylyn;

import static org.junit.Assert.assertNotNull;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import org.junit.Test;

/**
 * Simple test to guard against build setup errors such as --release not being
 * effective.
 */
public class BreeSmokeTest {

	@Test
	public void testByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(10);
		Buffer flipped = buffer.flip();
		assertNotNull(flipped);
	}
}
