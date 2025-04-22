        }

        else if (fast) {
            if (ratio == IPageLayout.NULL_RATIO) {
                pageLayout.addFastView(id);
            } else {
                pageLayout.addFastView(id, ratio);
            }
