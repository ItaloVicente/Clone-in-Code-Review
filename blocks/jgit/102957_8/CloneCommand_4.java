			System.out.println(
					"branchesToClone.size()='" + branchesToClone.size() + "'");
			System.out.println("wcrs='" + wcrs + "'");
			for (final String selectedRef : branchesToClone) {
				System.out.println("wcrs.matchSource('" + selectedRef + "')="
						+ wcrs.matchSource(selectedRef));
				if (wcrs.matchSource(selectedRef)) {

