/*
 * Copyright (C) 2020 Thomas Wolf <thomas.wolf@paranor.ch> and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.eclipse.jgit.transport;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;

public class JSchSshProtocol2Test extends JSchSshTest {

	@Override
	public void setUp() throws Exception {
		super.setUp();
		StoredConfig config = ((Repository) db).getConfig();
		config.setInt("protocol", null, "version", 2);
		config.save();
	}
}
