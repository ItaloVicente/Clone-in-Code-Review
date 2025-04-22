            actionContributionItem.fill(parent, index);
        }

        @Override
        public void dispose() {
            super.dispose();
            actionContributionItem.dispose();
        }
    }

    private static class AlwaysDisabledExpression extends Expression {
        @Override
