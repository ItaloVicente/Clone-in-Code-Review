	@Override
    public Statement apply(Statement base, Description description) {
        return new MyStatement(base);
    }

    public class MyStatement extends Statement {
        private final Statement base;

        public MyStatement(Statement base) {
            this.base = base;
        }

        @Override
        public void evaluate() throws Throwable {
			applicationContext = createApplicationContext();
            try {
                base.evaluate();
            } finally {
				applicationContext.dispose();
            }
        }
    }

