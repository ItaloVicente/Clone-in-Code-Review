			this.rawSmudgeFilterCommand = smudgeFilterCommand;
			this.path = path;
		}

		public String getSmudgeFilterCommand() {
			if (rawSmudgeFilterCommand == null) {
				return null;
			}
			return rawSmudgeFilterCommand.replaceAll("%f"
					QuotedString.BOURNE.quote(path));
