/*
 * Copyright (C) 2010, Red Hat Inc.
 * and other copyright owners as documented in the project's IP log.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Distribution License v1.0 which
 * accompanies this distribution, is reproduced below, and is
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials provided
 *   with the distribution.
 *
 * - Neither the name of the Eclipse Foundation, Inc. nor the
 *   names of its contributors may be used to endorse or promote
 *   products derived from this software without specific prior
 *   written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.eclipse.jgit.ignore;

import org.eclipse.jgit.errors.InvalidPatternException;
import org.eclipse.jgit.fnmatch.FileNameMatcher;

/**
 * A single ignore rule corresponding to one line in a .gitignore or ignore
 * file. Parses the ignore pattern
 *
 * Inspiration from: Ferry Huberts
 *
 * @deprecated this rule does not support double star pattern and is slow
 *             parsing glob expressions. Consider to use {@link FastIgnoreRule}
 */
@Deprecated
public class IgnoreRule {
	private String pattern;
	private boolean negation;
	private boolean nameOnly;
	private boolean dirOnly;
	private FileNameMatcher matcher;

	/**
	 * Create a new ignore rule with the given pattern. Assumes that
	 * the pattern is already trimmed.
	 *
	 * @param pattern
	 * 			  Base pattern for the ignore rule. This pattern will
	 * 			  be parsed to generate rule parameters.
	 */
	public IgnoreRule (String pattern) {
		this.pattern = pattern;
		negation = false;
		nameOnly = false;
		dirOnly = false;
		matcher = null;
		setup();
	}

	/**
	 * Remove leading/trailing characters as needed. Set up
	 * rule variables for later matching.
	 */
	private void setup() {
		int startIndex = 0;
		int endIndex = pattern.length();
			startIndex++;
			negation = true;
		}

			endIndex --;
			dirOnly = true;
		}

		pattern = pattern.substring(startIndex, endIndex);

		if (!hasSlash)
			nameOnly = true;
		}

			try {
				matcher = new FileNameMatcher(pattern, Character.valueOf('/'));
			} catch (InvalidPatternException e) {
			}
		}
	}


	/**
	 * @return
	 * 			  True if the pattern is just a file name and not a path
	 */
	public boolean getNameOnly() {
		return nameOnly;
	}

	/**
	 *
	 * @return
	 * 			  True if the pattern should match directories only
	 */
	public boolean dirOnly() {
		return dirOnly;
	}

	/**
	 *
	 * @return
	 * 			  True if the pattern had a "!" in front of it
	 */
	public boolean getNegation() {
		return negation;
	}

	/**
	 * @return
	 * 			  The blob pattern to be used as a matcher
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * Returns true if a match was made.
	 * <br>
	 * This function does NOT return the actual ignore status of the
	 * target! Please consult {@link #getResult()} for the ignore status. The actual
	 * ignore status may be true or false depending on whether this rule is
	 * an ignore rule or a negation rule.
	 *
	 * @param target
	 * 			  Name pattern of the file, relative to the base directory of this rule
	 * @param isDirectory
	 * 			  Whether the target file is a directory or not
	 * @return
	 * 			  True if a match was made. This does not necessarily mean that
	 * 			  the target is ignored. Call {@link IgnoreRule#getResult() getResult()} for the result.
	 */
	public boolean isMatch(String target, boolean isDirectory) {

		if (matcher == null) {
			if (target.equals(pattern)) {
				if (dirOnly && !isDirectory)
					return false;
				else
					return true;
			}

			/*
			 * Add slashes for startsWith check. This avoids matching e.g.
			 * "/src/new" to /src/newfile" but allows "/src/new" to match
			 * "/src/new/newfile", as is the git standard
			 */
				return true;

			if (nameOnly) {
				for (int idx = 0; idx < segments.length; idx++) {
					final String segmentName = segments[idx];
					if (segmentName.length() == 0)
						continue;
					if (segmentName.equals(pattern) &&
							doesMatchDirectoryExpectations(isDirectory, idx, segments.length))
						return true;
				}
			}

		} else {
			matcher.reset();
			matcher.append(target);
			if (matcher.isMatch())
				return true;

			if (nameOnly) {
				for (int idx = 0; idx < segments.length; idx++) {
					final String segmentName = segments[idx];
					if (segmentName.length() == 0)
						continue;
					matcher.reset();
					matcher.append(segmentName);
					if (matcher.isMatch() &&
							doesMatchDirectoryExpectations(isDirectory, idx, segments.length))
						return true;
				}
			} else {
				matcher.reset();

				for (int idx = 0; idx < segments.length; idx++) {
					final String segmentName = segments[idx];
					if (segmentName.length() == 0)
						continue;


					if (matcher.isMatch()
							&& doesMatchDirectoryExpectations(isDirectory, idx,
									segments.length))
						return true;
				}
			}
		}

		return false;
	}

	/**
	 * If a call to <code>isMatch(String, boolean)</code> was previously
	 * made, this will return whether or not the target was ignored. Otherwise
	 * this just indicates whether the rule is non-negation or negation.
	 *
	 * @return
	 * 			  True if the target is to be ignored, false otherwise.
	 */
	public boolean getResult() {
		return !negation;
	}

	private boolean doesMatchDirectoryExpectations(boolean isDirectory, int segmentIdx, int segmentLength) {
		if (segmentIdx < segmentLength - 1) {
			return true;
		}

		return !dirOnly || isDirectory;
	}

	@Override
	public String toString() {
		return pattern;
	}
}
