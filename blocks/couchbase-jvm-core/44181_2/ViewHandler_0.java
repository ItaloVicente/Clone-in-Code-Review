            if (queryMsg.spatial()) {
                path.append("/_spatial/");
            } else {
                path.append("/_view/");
            }
            path.append(queryMsg.view());
