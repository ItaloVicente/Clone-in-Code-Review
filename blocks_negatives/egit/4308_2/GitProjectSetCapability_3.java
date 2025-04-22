				} catch (final InvocationTargetException e) {
					throw TeamException.asTeamException(e);
				} catch (final CoreException e) {
					throw TeamException.asTeamException(e);
				} catch (final InterruptedException e) {
					return new IProject[0];
