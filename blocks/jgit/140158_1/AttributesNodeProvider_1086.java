package org.eclipse.jgit.attributes;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttributesNode {
	private final List<AttributesRule> rules;

	public AttributesNode() {
		rules = new ArrayList<>();
	}

	public AttributesNode(List<AttributesRule> rules) {
		this.rules = rules;
	}

	public void parse(InputStream in) throws IOException {
		BufferedReader br = asReader(in);
		String txt;
		while ((txt = br.readLine()) != null) {
			txt = txt.trim();
				int patternEndSpace = txt.indexOf(' ');
				int patternEndTab = txt.indexOf('\t');

				final int patternEnd;
				if (patternEndSpace == -1)
					patternEnd = patternEndTab;
				else if (patternEndTab == -1)
					patternEnd = patternEndSpace;
				else
					patternEnd = Math.min(patternEndSpace

				if (patternEnd > -1)
					rules.add(new AttributesRule(txt.substring(0
							txt.substring(patternEnd + 1).trim()));
			}
		}
	}

	private static BufferedReader asReader(InputStream in) {
		return new BufferedReader(new InputStreamReader(in
	}

	public List<AttributesRule> getRules() {
		return Collections.unmodifiableList(rules);
	}

}
