			if (isInteractive()) {
				interactiveHandler.prepareSteps(steps);
				BufferedWriter fw = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(new File(
								rebaseDir
								Constants.CHARACTER_ENCODING));
				fw.write("# Created by EGit: rebasing " + upstreamCommit.name());
				fw.newLine();
				try {
					StringBuilder sb = new StringBuilder();
					for (Step step : steps) {
						sb.setLength(0);
						sb.append(step.action.token);
						sb.append(" ");
						sb.append(step.commit.name());
						sb.append(" ");
						sb.append(new String(step.shortMessage).trim());
						fw.write(sb.toString());
						fw.newLine();
					}
				} finally {
					fw.close();
				}
			}
