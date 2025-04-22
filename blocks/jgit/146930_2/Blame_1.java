				ObjectId head = db.resolve(Constants.HEAD);
				if (head == null) {
					throw die(MessageFormat.format(CLIText.get().noSuchRef
							Constants.HEAD));
				}
				generator.push(null
