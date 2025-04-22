				ExecutionResult executionResult = mergeToolMgr.merge(local,
						remote, merged, base, tempDir, toolName, prompt, gui);
				outw.println(
						new String(executionResult.getStdout().toByteArray()));
				outw.flush();
				errw.println(
						new String(executionResult.getStderr().toByteArray()));
				errw.flush();
