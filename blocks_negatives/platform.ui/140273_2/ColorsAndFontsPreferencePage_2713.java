                    String catId = colorDefinitions[i].getCategoryId();
                    if ((catId == null && categoryId == null)
                            || (catId != null && categoryId != null && categoryId
                                    .equals(catId))) {
                        if (colorDefinitions[i].getDefaultsTo() != null
                                && parentIsInSameCategory(colorDefinitions[i])) {
