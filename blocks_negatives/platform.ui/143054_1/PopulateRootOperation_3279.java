                Iterator childrenEnum = children.iterator();
                while (childrenEnum.hasNext()) {
                    createElement(result, childrenEnum.next(), depth - 1);
                }
                result.setPopulated();
            }
