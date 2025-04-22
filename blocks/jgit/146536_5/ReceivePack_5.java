				ReceiveCommand cmd = ReceivePack.parseCommand(line);
				if (cmd.getRefName().equals(Constants.HEAD)) {
					cmd.setResult(Result.REJECTED_CURRENT_BRANCH);
				} else {
					cmd.setRef(refs.get(cmd.getRefName()));
				}
				commands.add(cmd);
				if (certParser.enabled()) {
					certParser.addCommand(cmd);
				}
			}
			pushCert = certParser.build();
			if (hasCommands()) {
				readPostCommands(pck);
			}
		} catch (PackProtocolException e) {
			discardCommands();
			fatalError(e.getMessage());
			throw e;
		} catch (InputOverLimitIOException e) {
			String msg = JGitText.get().tooManyCommands;
			discardCommands();
			fatalError(msg);
			throw new PackProtocolException(msg);
		}
	}
