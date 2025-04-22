				if (proposals == null) {
					return null;
				}
				List<IContentProposal> resultList = new ArrayList<>();
				Pattern pattern = UIUtils.createProposalPattern(contents);
				for (final Change ref : proposals) {
					if (pattern != null && !pattern
							.matcher(ref.getChangeNumber().toString())
							.matches()) {
						continue;
