				boolean needsNewInput = tv.getInput() == null;
				List oldInput = (List) tv.getInput();
				if (!needsNewInput)
					needsNewInput = oldInput.size() != input.size();

				if (!needsNewInput) {
					for (int i = 0; i < input.size(); i++) {
						needsNewInput = !input.get(i).equals(oldInput.get(i));
						if (needsNewInput)
							break;
					}
				}

				final boolean updateInput = needsNewInput;

