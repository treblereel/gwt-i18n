package org.gwtproject.i18n.client;

import org.gwtproject.i18n.client.I18N;
import org.gwtproject.i18n.client.Messages;
import org.gwtproject.safehtml.shared.SafeHtml;

import static org.gwtproject.i18n.client.LocalizableResource.*;

/**
 * @author Dmitrii Tikhomirov
 * Created by treblereel 1/6/20
 */
@I18N
@DefaultLocale
interface Nested extends Messages {

    @DefaultMessage("nested dollar")
    String nestedDollar();

    @DefaultMessage("nested dollar")
    @Key("nestedDollar")
    SafeHtml nestedDollarAsSafeHtml();

    @DefaultMessage("nested underscore")
    String nestedUnderscore();

    @DefaultMessage("nested underscore")
    @Key("nestedUnderscore")
    SafeHtml nestedUnderscoreAsSafeHtml();
}
