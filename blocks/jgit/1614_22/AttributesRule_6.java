package org.eclipse.jgit.attributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.jgit.lib.Constants;

public class AttributesNode {
	private final List<AttributesRule> rules;

	public AttributesNode() {
		rules = new ArrayList<AttributesRule>();
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

	public void getAttributes(String entryPath
			Map<String
		ListIterator<AttributesRule> ruleIterator = rules.listIterator(rules
				.size());
		while (ruleIterator.hasPrevious()) {
			AttributesRule rule = ruleIterator.previous();
			if (rule.isMatch(entryPath
				ListIterator<Attribute> attributeIte = rule.getAttributes()
						.listIterator(rule.getAttributes().size());
				while (attributeIte.hasPrevious()) {
					Attribute attr = attributeIte.previous();
					if (!attributes.containsKey(attr.getKey())) {
						attributes.put(attr.getKey()
					}
				}
			}
		}
	}
}
