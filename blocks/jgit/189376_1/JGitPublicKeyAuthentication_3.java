	private void parseAddKeys(HostConfigEntry config) {
		String value = config.getProperty(SshConstants.ADD_KEYS_TO_AGENT);
		if (StringUtils.isEmptyOrNull(value)) {
			addKeysToAgent = false;
			return;
		}
		String[] values = value.split("
			addKeysToAgent = true;
			break;
			addKeysToAgent = false;
			break;
			addKeysToAgent = true;
			askBeforeAdding = true;
			break;
			addKeysToAgent = true;
			rules.add(SshAgentKeyConstraint.CONFIRM);
			if (values.length > 1) {
				int seconds = OpenSshConfigFile.timeSpec(values[1]);
				if (seconds > 0) {
					rules.add(new SshAgentKeyConstraint.LifeTime(seconds));
				}
			}
			break;
		default:
			int seconds = OpenSshConfigFile.timeSpec(values[0]);
			if (seconds > 0) {
				addKeysToAgent = true;
				rules.add(new SshAgentKeyConstraint.LifeTime(seconds));
			}
			break;
		}
		constraints = rules.toArray(new SshAgentKeyConstraint[0]);
	}

