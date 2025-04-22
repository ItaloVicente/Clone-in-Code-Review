                                            for (int j = 0; j < pages.length; j++) {
                                                IWorkbenchPage page = pages[j];
                                                IViewReference[] refs = page
                                                        .getViewReferences();
                                                for (int k = 0; k < refs.length; k++) {
                                                    IViewPart part = refs[k]
                                                            .getView(false);
