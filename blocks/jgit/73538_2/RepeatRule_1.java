package org.eclipse.jgit.junit;

import java.text.MessageFormat;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RepeatRule implements TestRule {

	public static class RepeatedTestException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public RepeatedTestException(String message
			super(message
		}
	}

	private static class RepeatStatement extends Statement {

		private final int repetitions;

		private final Statement statement;

		private RepeatStatement(int repetitions
			this.repetitions = repetitions;
			this.statement = statement;
		}

		@Override
		public void evaluate() throws Throwable {
			for (int i = 0; i < repetitions; i++) {
				try {
				statement.evaluate();
				} catch (Throwable e) {
					throw new RepeatedTestException(MessageFormat.format(
							"Repeated test failed when run for the {0}. time"
							Integer.valueOf(i + 1))
							e);
				}
			}
		}
	}

	@Override
	public Statement apply(Statement statement
		Statement result = statement;
		Repeat repeat = description.getAnnotation(Repeat.class);
		if (repeat != null) {
			int n = repeat.n();
			result = new RepeatStatement(n
		}
		return result;
	}
}
