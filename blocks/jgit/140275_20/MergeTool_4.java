				Optional<ExecutionResult> optionalResult = mergeTools.merge(
						local
						gui
				if (optionalResult.isPresent()) {
					ExecutionResult result = optionalResult.get();
					outw.println(new String(result.getStdout().toByteArray()));
					outw.flush();
					errw.println(new String(result.getStderr().toByteArray()));
					errw.flush();
				} else {
					return MergeResult.ABORT;
				}
