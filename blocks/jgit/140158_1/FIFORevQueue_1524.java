
package org.eclipse.jgit.revwalk;

class EndGenerator extends Generator {
	static final EndGenerator INSTANCE = new EndGenerator();

	private EndGenerator() {
	}

	@Override
	RevCommit next() {
		return null;
	}

	@Override
	int outputType() {
		return 0;
	}
}
