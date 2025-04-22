                } catch(Throwable t){
                	throwable = t;
                } finally {
               		UIStats.end(UIStats.UI_JOB, UIJob.this, getName());
                    if (result == null) {
						result = new Status(IStatus.ERROR,
                                PlatformUI.PLUGIN_ID, IStatus.ERROR,
                                ProgressMessages.InternalError,
                                throwable);
					}
                    done(result);
                }
            }
        });
