	private final class BranchValidator implements IValidator {
		@Override
		public IStatus validate(Object value) {
			if (value == null || !isValidRefName(R_HEADS + value)) {
				return error(NLS.bind(InitDialog_invalidBranchName, value));
			}
			return Status.OK_STATUS;
		}
	}

	private final class BranchExistsValidator implements IValidator {
		private List<String> list;

		public BranchExistsValidator(List<Ref> branchList) {
			list = new ArrayList<>();
			for (Ref ref : branchList) {
				list.add(ref.getName());
			}
		}

		@Override
		public IStatus validate(Object value) {
			if (value == null || !list.contains(R_HEADS + value)) {
				return warning(NLS.bind(InitDialog_branchDoesNotExistYetAndWillBeCreated, value));
			}
			return Status.OK_STATUS;
		}
	}

