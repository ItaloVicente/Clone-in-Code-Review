	public class MockOutputStream extends OutputStream {

		private int foo;

		public void setFoo(int foo) {
			this.foo = foo;
		}

		public int getFoo() {
			return foo;
		}
