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
 * Pre-defined merge tools.
 *
 * Adds same merge tools as also pre-defined in C-Git
 * see "git-core\mergetools\"
 * see links to command line parameter description for the tools
 *
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
public enum PreDefinedMergeTools {
	/**
	 */
	araxis("compare",
			"-wait -merge -3 -a1 \"$BASE\" \"$LOCAL\" \"$REMOTE\" \"$MERGED\"",
			"-wait -2 \"$LOCAL\" \"$REMOTE\" \"$MERGED\"",
			false),
	/**
	 */
	bc("bcomp", "\"$LOCAL\" \"$REMOTE\" \"$BASE\" --mergeoutput=\"$MERGED\"",
			"\"$LOCAL\" \"$REMOTE\" --mergeoutput=\"$MERGED\"",
			false),
	/**
	 */
	bc3("bcompare", bc),
	/**
	 */
	codecompare("CodeMerge",
			"-MF=\"$LOCAL\" -TF=\"$REMOTE\" -BF=\"$BASE\" -RF=\"$MERGED\"",
			"-MF=\"$LOCAL\" -TF=\"$REMOTE\" -RF=\"$MERGED\"",
			false),
	/**
	 * @hint: $(pwd) command must be defined
	 */
	deltawalker("DeltaWalker",
			"\"$LOCAL\" \"$REMOTE\" \"$BASE\" -pwd=\"$(pwd)\" -merged=\"$MERGED\"",
			"\"$LOCAL\" \"$REMOTE\" -pwd=\"$(pwd)\" -merged=\"$MERGED\"",
			true),
	/**
	 */
	diffmerge("diffmerge", //$NON-NLS-1$
			"--merge --result=\"$MERGED\" \"$LOCAL\" \"$BASE\" \"$REMOTE\"",
			"--merge --result=\"$MERGED\" \"$LOCAL\" \"$REMOTE\"",
			true),
	/**
	 * @hint: check the ' | cat' for the call
	 */
	diffuse("diffuse", "\"$LOCAL\" \"$MERGED\" \"$REMOTE\" \"$BASE\"",
			"\"$LOCAL\" \"$MERGED\" \"$REMOTE\"", false),
	/**
	 */
	ecmerge("ecmerge",
			"--default --mode=merge3 \"$BASE\" \"$LOCAL\" \"$REMOTE\" --to=\"$MERGED\"",
			"--default --mode=merge2 \"$LOCAL\" \"$REMOTE\" --to=\"$MERGED\"",
			false),
	/**
	 * @hint: $(basename) command must be defined
	 */
	emerge("emacs",
			"-f emerge-files-with-ancestor-command \"$LOCAL\" \"$REMOTE\" \"$BASE\" \"$(basename \"$MERGED\")\"",
			"-f emerge-files-command \"$LOCAL\" \"$REMOTE\" \"$(basename \"$MERGED\")\"",
			true),
	/**
	 */
	examdiff("ExamDiff",
			"-merge \"$LOCAL\" \"$BASE\" \"$REMOTE\" -o:\"$MERGED\" -nh",
			"-merge \"$LOCAL\" \"$REMOTE\" -o:\"$MERGED\" -nh",
			false),
	/**
	 */
	guiffy("guiffy", "-s \"$LOCAL\" \"$REMOTE\" \"$BASE\" \"$MERGED\"",
			"-m \"$LOCAL\" \"$REMOTE\" \"$MERGED\"", true),
	/**
	 */
	gvimdiff("gvim",
			"-f -d -c '4wincmd w | wincmd J' \"$LOCAL\" \"$BASE\" \"$REMOTE\" \"$MERGED\"",
			"-f -d -c 'wincmd l' \"$LOCAL\" \"$MERGED\" \"$REMOTE\"",
			true),
	/**
	 */
	gvimdiff2("gvim", "-f -d -c 'wincmd l' \"$LOCAL\" \"$MERGED\" \"$REMOTE\"",
			"-f -d -c 'wincmd l' \"$LOCAL\" \"$MERGED\" \"$REMOTE\"", true),
	/**
	 */
	gvimdiff3("gvim",
			"-f -d -c 'hid | hid | hid' \"$LOCAL\" \"$REMOTE\" \"$BASE\" \"$MERGED\"",
			"-f -d -c 'hid | hid' \"$LOCAL\" \"$REMOTE\" \"$MERGED\"", true),
	/**
	 */
	kdiff3("kdiff3",
			"--auto --L1 \"$MERGED (Base)\" --L2 \"$MERGED (Local)\" --L3 \"$MERGED (Remote)\" -o \"$MERGED\" \"$BASE\" \"$LOCAL\" \"$REMOTE\"",
			"--auto --L1 \"$MERGED (Local)\" --L2 \"$MERGED (Remote)\" -o \"$MERGED\" \"$LOCAL\" \"$REMOTE\"",
			true),
	/**
	 * @hint: cannot merge
	 */
	/**
	 * @hint: use meld with output option only (new versions)
	 */
	meld("meld", "--output=\"$MERGED\" \"$LOCAL\" \"$BASE\" \"$REMOTE\"",
			"\"$LOCAL\" \"$MERGED\" \"$REMOTE\"",
			false),
	/**
	 * @hint: check the ' | cat' for the call
	 */
	opendiff("opendiff",
			"\"$LOCAL\" \"$REMOTE\" -ancestor \"$BASE\" -merge \"$MERGED\"",
			"\"$LOCAL\" \"$REMOTE\" -merge \"$MERGED\"",
			false),
	/**
	 * @hint: check how to fix "no base present" / create_virtual_base problem
	 */
	p4merge("p4merge", "\"$BASE\" \"$REMOTE\" \"$LOCAL\" \"$MERGED\"",
			"\"$REMOTE\" \"$LOCAL\" \"$MERGED\"", false),
	/**
	 */
	tkdiff("tkdiff", "-a \"$BASE\" -o \"$MERGED\" \"$LOCAL\" \"$REMOTE\"",
			"-o \"$MERGED\" \"$LOCAL\" \"$REMOTE\"",
			true),
	/**
	 * @hint: merge without base is not supported
	 * @hint: cannot diff
	 */
	tortoisemerge("tortoisemerge",
			"-base:\"$BASE\" -mine:\"$LOCAL\" -theirs:\"$REMOTE\" -merged:\"$MERGED\"",
			null,
			false),
	/**
	 * @hint: merge without base is not supported
	 * @hint: cannot diff
	 */
	tortoisegitmerge("tortoisegitmerge",
			"-base \"$BASE\" -mine \"$LOCAL\" -theirs \"$REMOTE\" -merged \"$MERGED\"",
			null, false),
	/**
	 */
	vimdiff("vim", gvimdiff),
	/**
	 */
	vimdiff2("vim", gvimdiff2),
	/**
	 */
	vimdiff3("vim", gvimdiff3),
	/**
	 * @hint: check how 'mergetool_find_win32_cmd "WinMergeU.exe" "WinMerge"'
	 *        works
	 */
	winmerge("WinMergeU",
			"-u -e -dl Local -dr Remote \"$LOCAL\" \"$REMOTE\" \"$MERGED\"",
			"-u -e -dl Local -dr Remote \"$LOCAL\" \"$REMOTE\" \"$MERGED\"",
			false),
	/**
	 */
	xxdiff("xxdiff",
			"-X --show-merged-pane -R 'Accel.SaveAsMerged: \"Ctrl+S\"' -R 'Accel.Search: \"Ctrl+F\"' -R 'Accel.SearchForward: \"Ctrl+G\"' --merged-file \"$MERGED\" \"$LOCAL\" \"$BASE\" \"$REMOTE\"",
			"-X -R 'Accel.SaveAsMerged: \"Ctrl+S\"' -R 'Accel.Search: \"Ctrl+F\"' -R 'Accel.SearchForward: \"Ctrl+G\"' --merged-file \"$MERGED\" \"$LOCAL\" \"$REMOTE\"",
			false);

	PreDefinedMergeTools(String path, String parametersWithBase,
			String parametersWithoutBase,
			boolean exitCodeTrustable) {
		this.path = path;
		this.parametersWithBase = parametersWithBase;
		this.parametersWithoutBase = parametersWithoutBase;
		this.exitCodeTrustable = exitCodeTrustable;
    }

	PreDefinedMergeTools(PreDefinedMergeTools from) {
		this(from.getPath(), from.getParameters(true),
				from.getParameters(false), from.isExitCodeTrustable());
	}

	PreDefinedMergeTools(String path, PreDefinedMergeTools from) {
		this(path, from.getParameters(true), from.getParameters(false),
				from.isExitCodeTrustable());
	}

	private String path;

	private String parametersWithBase;

	private String parametersWithoutBase;

	private boolean exitCodeTrustable;

	/**
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param withBase
	 *            return parameters with base present?
	 * @return parameters with or without base present
	 */
	public String getParameters(boolean withBase) {
		if (withBase) {
			return parametersWithBase;
		}
		return parametersWithoutBase;
	}

	/**
	 * @return parameters
	 */
	public boolean isExitCodeTrustable() {
		return exitCodeTrustable;
	}

	/**
	 * @return true if command with base present is valid, false otherwise
	 */
	public boolean canMergeWithoutBasePresent() {
		return parametersWithoutBase != null;
	}

}
