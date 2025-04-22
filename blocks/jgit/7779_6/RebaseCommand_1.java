			if (isInteractive()) {
				interactiveHandler.prepareSteps(steps);
				BufferedWriter fw = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(new File(
								rebaseDir
								Constants.CHARACTER_ENCODING));
				fw.newLine();
				try {
					StringBuilder sb = new StringBuilder();
					for (Step step : steps) {
						sb.setLength(0);
						sb.append(step.action.token);
						sb.append(" ");
						sb.append(step.commit.name());
						sb.append(" ");
						sb.append(new String(step.shortMessage
								Constants.CHARACTER_ENCODING).trim());
						fw.write(sb.toString());
						fw.newLine();
					}
				} finally {
					fw.close();
				}
			}
