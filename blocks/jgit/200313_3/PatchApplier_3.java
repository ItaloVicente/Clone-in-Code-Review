		public static class Error {
			private String msg;
			private String oldFileName;
			private @Nullable HunkHeader hh;

			private Error(String msg
				this.msg = msg;
				this.oldFileName = oldFileName;
				this.hh = hh;
			}

			@Override
			public String toString() {
				if(hh != null) {
					return MessageFormat.format("{0}. In file: {1}
							msg
				}
				return MessageFormat.format("{0}. In file: {1}."
			}

		}

