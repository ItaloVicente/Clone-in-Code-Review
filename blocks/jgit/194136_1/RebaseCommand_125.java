				newHead = git.commit()
						.setMessage(newMessage)
						.setAmend(true)
						.setNoVerify(true)
						.setInsertChangeId(doChangeId[0])
						.call();
