                        if (response.status() == ResponseStatus.ACCESS_ERROR) {
                            String details = ResponseStatusDetails.stringify(response.status(), response.statusDetails());
                            throw new AuthenticationException("The application is not authorized to perform the \"observe\" "
                                    + "operation, make sure you have read privileges on this bucket: " + details);
                        }

