	/**
	 * Describes rebase actions
	 */
	public static enum Action {
		/** Use commit */
		PICK("pick", "p"), //$NON-NLS-1$ //$NON-NLS-2$
		/** Use commit, but edit the commit message */
		REWORD("reword", "r"), //$NON-NLS-1$ //$NON-NLS-2$
		/** Use commit, but stop for amending */
		EDIT("edit", "e"); // later add SQUASH, FIXUP, etc. //$NON-NLS-1$ //$NON-NLS-2$

		private final String token;

		private final String shortToken;

		private Action(String token, String shortToken) {
			this.token = token;
			this.shortToken = shortToken;
		}

		/**
		 * @return full action token name
		 */
		public String toToken() {
			return this.token;
		}

		@SuppressWarnings("nls")
		@Override
		public String toString() {
			return "Action[" + token + "]";
		}

		static Action parse(String token) {
			for (Action action : Action.values()) {
				if (action.token.equals(token)
						|| action.shortToken.equals(token))
					return action;
			}
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().unknownOrUnsupportedCommand, token,
					Action.values()));
		}
	}

	/**
	 * Describes single rebase step
	 */
	public static class Step {
		Action action;

		AbbreviatedObjectId commit;

		byte[] shortMessage;

		Step(Action action) {
			this.action = action;
		}

		/**
		 * @return rebase action type
		 */
		public Action getAction() {
			return action;
		}

		/**
		 * @param action
		 */
		public void setAction(Action action) {
			this.action = action;
		}

		/**
		 * @return abbreviated commit SHA-1 of commit that action will be
		 *         performed on
		 */
		public AbbreviatedObjectId getCommit() {
			return commit;
		}

		/**
		 * @return short message commit of commit that action will be performed
		 *         on
		 */
		public byte[] getShortMessage() {
			return shortMessage;
		}

		@SuppressWarnings("nls")
		@Override
		public String toString() {
			return "Step["
					+ action
					+ ", "
					+ ((commit == null) ? "null" : commit)
					+ ", "
					+ ((shortMessage == null) ? "null" : new String(
							shortMessage)) + "]";
		}
	}
