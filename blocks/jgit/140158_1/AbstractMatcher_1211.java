package org.eclipse.jgit.ignore;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IgnoreNode {
	public static enum MatchResult {
		NOT_IGNORED

		IGNORED

		CHECK_PARENT

		CHECK_PARENT_NEGATE_FIRST_MATCH;
	}

	private final List<FastIgnoreRule> rules;

	public IgnoreNode() {
		rules = new ArrayList<>();
	}

	public IgnoreNode(List<FastIgnoreRule> rules) {
		this.rules = rules;
	}

	public void parse(InputStream in) throws IOException {
		BufferedReader br = asReader(in);
		String txt;
		while ((txt = br.readLine()) != null) {
				FastIgnoreRule rule = new FastIgnoreRule(txt);
				if (!rule.isEmpty()) {
					rules.add(rule);
				}
			}
		}
	}

	private static BufferedReader asReader(InputStream in) {
		return new BufferedReader(new InputStreamReader(in
	}

	public List<FastIgnoreRule> getRules() {
		return Collections.unmodifiableList(rules);
	}

	public MatchResult isIgnored(String entryPath
		final Boolean result = checkIgnored(entryPath
		if (result == null) {
			return MatchResult.CHECK_PARENT;
		}

		return result.booleanValue() ? MatchResult.IGNORED
				: MatchResult.NOT_IGNORED;
	}

	public Boolean checkIgnored(String entryPath
		for (int i = rules.size() - 1; i > -1; i--) {
			FastIgnoreRule rule = rules.get(i);
			if (rule.isMatch(entryPath
				return Boolean.valueOf(rule.getResult());
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return rules.toString();
	}
}
