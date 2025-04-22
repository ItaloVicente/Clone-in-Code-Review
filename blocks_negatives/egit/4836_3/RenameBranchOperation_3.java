				} catch (RefNotFoundException e) {
					throw new CoreException(Activator.error(e.getMessage(), e));
				} catch (InvalidRefNameException e) {
					throw new CoreException(Activator.error(e.getMessage(), e));
				} catch (RefAlreadyExistsException e) {
					throw new CoreException(Activator.error(e.getMessage(), e));
				} catch (DetachedHeadException e) {
