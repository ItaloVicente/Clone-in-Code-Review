/*
 * Copyright (C) 2018-2020, Andre Bossert <andre.bossert@siemens.com>
 * and other copyright owners as documented in the project's IP log.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.eclipse.jgit.diffmergetool;

/**
 * Pre-defined diff tools.
 *
 * Adds same diff tools as also pre-defined in C-Git
 * see "git-core\mergetools\"
 * see links to command line parameter description for the tools

 * @formatter:off
 *
 * araxis
 * bc
 * bc3
 * codecompare
 * deltawalker
 * diffmerge
 * diffuse
 * ecmerge
 * emerge
 * examdiff
 * guiffy
 * gvimdiff
 * gvimdiff2
 * gvimdiff3
 * kdiff3
 * kompare
 * meld
 * opendiff
 * p4merge
 * tkdiff
 * tortoisemerge
 * vimdiff
 * vimdiff2
 * vimdiff3
 * winmerge
 * xxdiff
 *
 * @formatter:on
 *
 * @since 5.7
 *
 */
@SuppressWarnings("nls")
public enum PreDefinedDiffTools {
	/**
	 */
	araxis("compare", "-wait -2 \"$LOCAL\" \"$REMOTE\""),
	/**
	 */
	bc("bcomp", "\"$LOCAL\" \"$REMOTE\""),
	/**
	 */
	bc3("bcompare", bc),
	/**
	 */
	codecompare("CodeCompare", "\"$LOCAL\" \"$REMOTE\""),
	/**
	 */
	deltawalker("DeltaWalker", "\"$LOCAL\" \"$REMOTE\""),
	/**
	 */
	diffmerge("diffmerge", "\"$LOCAL\" \"$REMOTE\""),
	/**
	 */
	diffuse("diffuse", "\"$LOCAL\" \"$REMOTE\""),
	/**
	 */
	ecmerge("ecmerge", "--default --mode=diff2 \"$LOCAL\" \"$REMOTE\""),
	/**
	 */
	emerge("emacs", "-f emerge-files-command \"$LOCAL\" \"$REMOTE\""),
	/**
	 */
	examdiff("ExamDiff", "\"$LOCAL\" \"$REMOTE\" -nh"),
	/**
	 */
	guiffy("guiffy", "\"$LOCAL\" \"$REMOTE\""),
	/**
	 */
	gvimdiff("gviewdiff", "\"$LOCAL\" \"$REMOTE\""),
	/**
	 */
	gvimdiff2(gvimdiff),
	/**
	 */
	gvimdiff3(gvimdiff),
	/**
	 */
	kdiff3("kdiff3",
			"--L1 \"$MERGED (A)\" --L2 \"$MERGED (B)\" \"$LOCAL\" \"$REMOTE\""),
	/**
	 */
	kompare("kompare", "\"$LOCAL\" \"$REMOTE\""),
	/**
	 */
	meld("meld", "\"$LOCAL\" \"$REMOTE\""),
	/**
	 * @hint: check the ' | cat' for the call
	 */
	opendiff("opendiff", "\"$LOCAL\" \"$REMOTE\""),
	/**
	 */
	p4merge("p4merge", "\"$LOCAL\" \"$REMOTE\""),
	/**
	 */
	tkdiff("tkdiff", "\"$LOCAL\" \"$REMOTE\""),
	/**
	 * @hint: cannot diff
	 */
	/**
	 */
	vimdiff("viewdiff", gvimdiff),
	/**
	 */
	vimdiff2(vimdiff),
	/**
	 */
	vimdiff3(vimdiff),
	/**
	 * @hint: check how 'mergetool_find_win32_cmd "WinMergeU.exe" "WinMerge"'
	 *        works
	 */
	winmerge("WinMergeU", "-u -e \"$LOCAL\" \"$REMOTE\""),
	/**
	 */
	xxdiff("xxdiff",
			"-R 'Accel.Search: \"Ctrl+F\"' -R 'Accel.SearchForward: \"Ctrl+G\"' \"$LOCAL\" \"$REMOTE\"");

	PreDefinedDiffTools(String path, String parameters) {
		this.path = path;
		this.parameters = parameters;
    }

	PreDefinedDiffTools(PreDefinedDiffTools from) {
		this.path = from.getPath();
		this.parameters = from.getParameters();
	}

	PreDefinedDiffTools(String path, PreDefinedDiffTools from) {
		this.path = path;
		this.parameters = from.getParameters();
	}

	private String path;

	private String parameters;

	/**
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return parameters
	 */
	public String getParameters() {
		return parameters;
	}

}
