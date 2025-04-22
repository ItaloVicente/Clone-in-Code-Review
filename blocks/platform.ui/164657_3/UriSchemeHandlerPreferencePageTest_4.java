				return shell;
			}

			@Override
			IOperatingSystemRegistration getOperationgSystemRegistration() throws CoreException {
				if (operatingSystemRegistrationCreationExtension != null) {
					throw operatingSystemRegistrationCreationExtension;
				}
				return operatingSystemRegistrationMock;
