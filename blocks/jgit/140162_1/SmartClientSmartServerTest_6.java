		gs.setUploadPackFactory((HttpServletRequest req
                    DefaultUploadPackFactory f = new DefaultUploadPackFactory();
                    UploadPack up = f.create(req
                    if (advertiseRefsHook != null) {
                        up.setAdvertiseRefsHook(advertiseRefsHook);
                    }
                    return up;
                });
