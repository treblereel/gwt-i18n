package org.gwtproject.i18n.client;

/**
 * @author Dmitrii Tikhomirov
 * Created by treblereel 1/7/20
 */
/**
 * A custom plural rule that returns "0", "1", or "other".
 */
class CustomPluralRule implements PluralRule {
    @Override
    public PluralForm[] pluralForms() {
        return new PluralForm[] {
                new PluralForm("other", "other"),
                new PluralForm("0", "first"),
                new PluralForm("1", "second")
        };
    }

    @Override
    public int select(int n) {
        if (0 <= n && n <= 1) {
            return n + 1;
        }
        return 0;
    }
}
