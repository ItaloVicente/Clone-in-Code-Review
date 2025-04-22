            IViewReference reference = page
                    .findViewReference(IIntroConstants.INTRO_VIEW_ID);
            page.hideView(introView);
            if (reference == null || reference.getPart(false) == null) {
                introPart = null;
                return true;
            }
            return false;
        }
