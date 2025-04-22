package org.eclipse.jgit.attributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
				int patternEnd = txt.indexOf(' ');
				if(txt.length() > patternEnd)
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

	public List<Attribute> getAttributes(String entryPath
		if (rules.isEmpty())
			return Collections.emptyList();

		for (int i = rules.size() - 1; i > -1; i--) {
			AttributesRule rule = rules.get(i);
			if (rule.isMatch(entryPath
				return rule.getAttributes();
			}
		}
		return Collections.emptyList();
	}
}
