			private String createMessage() {
				StringBuilder b = new StringBuilder();

				String failureCause = cause.getHookStdErr();

				if (failureCause.length() > 0) {
					b.append(failureCause);
				} else {
					b.append(UIText.CommitJob_NoFailureReason);
				}

				if (hookOutput.length() > 0
						&& !hookOutput.equals(failureCause)) {
					b.append(System.lineSeparator());
					b.append(UIText.CommitJob_CompleteHookOutput);
					b.append(System.lineSeparator());
					b.append(hookOutput);
				}

				return b.toString();
			}

