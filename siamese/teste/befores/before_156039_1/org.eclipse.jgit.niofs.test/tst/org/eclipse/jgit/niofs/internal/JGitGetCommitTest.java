/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitGetCommitTest extends AbstractTestInfra {

    private Git git;

    private static final String MASTER_BRANCH = "master";

    @Before
    public void setup() throws IOException {
        final File parentFolder = createTempDirectory();

        final File gitSource = new File(parentFolder, "source/source.git");

        git = new CreateRepository(gitSource).execute().get();
    }

    @Test
    public void successTest() throws IOException {
        commit(git, MASTER_BRANCH, "Adding file", content("file.txt", "file content"));

        RevCommit lastCommit = git.getLastCommit(MASTER_BRANCH);

        RevCommit commit = git.getCommit(lastCommit.getName());

        assertThat(commit.getName()).isEqualTo(lastCommit.getName());
    }

    @Test
    public void notFoundTest() {
        RevCommit commit = git.getCommit("non-existent-commit-id");

        assertThat(commit).isNull();
    }
}