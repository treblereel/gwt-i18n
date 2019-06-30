package org.gwtproject.i18n.rg.rebind;

/**
 * @author Dmitrii Tikhomirov
 * Created by treblereel 6/30/19
 */
public enum Visibility {
    Public,
    Private {
        public boolean matches(Visibility visibility) {
            switch(visibility) {
                case LegacyDeploy:
                case Private:
                    return true;
                default:
                    return false;
            }
        }
    },
    Deploy {
        public boolean matches(Visibility visibility) {
            switch(visibility) {
                case LegacyDeploy:
                case Deploy:
                    return true;
                default:
                    return false;
            }
        }
    },
    Source,
    LegacyDeploy {
        public boolean matches(Visibility visibility) {
            switch(visibility) {
                case LegacyDeploy:
                case Private:
                case Deploy:
                    return true;
                default:
                    return false;
            }
        }
    };

    private Visibility() {
    }

    public boolean matches(Visibility visibility) {
        return this == visibility;
    }
}
