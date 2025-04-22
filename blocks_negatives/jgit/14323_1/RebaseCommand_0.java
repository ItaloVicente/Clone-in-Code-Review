			if (isInteractive()) {
				interactiveHandler.prepareSteps(steps);
				BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(
								rebaseState.getFile(GIT_REBASE_TODO)),
								Constants.CHARACTER_ENCODING));
				fw.newLine();
				try {
					StringBuilder sb = new StringBuilder();
					for (Step step : steps) {
						sb.setLength(0);
						sb.append(step.action.token);
						sb.append(step.commit.name());
						sb.append(RawParseUtils.decode(step.shortMessage)
								.trim());
						fw.write(sb.toString());
						fw.newLine();
					}
				} finally {
					fw.close();
				}
			}
