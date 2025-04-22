			switch (cherryPickResult.getStatus()) {
			case FAILED:
				if (operation == Operation.BEGIN)
					return abort(RebaseResult.failed(cherryPickResult
							.getFailingPaths()));
				else
