				if (proposals != null) {
					for (final Change ref : proposals) {
						if (pattern != null
								&& !pattern.matcher(
										ref.getChangeNumber().toString())
										.matches()) {
							continue;
						}
						resultList.add(new ChangeContentProposal(ref));
