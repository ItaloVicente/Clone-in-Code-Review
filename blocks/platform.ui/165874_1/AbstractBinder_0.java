
		public Boolean isAcceptValidationErrors() {
			return acceptValidationErrors == null ? false : acceptValidationErrors;
		}

		public void setAcceptValidationErrors(Boolean acceptValidationErrors) {
			Objects.requireNonNull(acceptValidationErrors);
			verifyNotSet(this.acceptValidationErrors);
			this.acceptValidationErrors = acceptValidationErrors;
		}
