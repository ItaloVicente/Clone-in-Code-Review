            if (visible) {
                if (VAL_TRUE.equals(standalone)) {
                    pageLayout.addStandaloneView(id, !VAL_FALSE
                            .equals(showTitle), intRelation, ratio, relative);
                } else {
                    pageLayout.addView(id, intRelation, ratio, relative, minimized);
                }
            } else {
