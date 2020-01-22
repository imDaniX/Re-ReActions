package pro.reactions.configuration.reload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Reloadable {
	ReloadType[] value() default {ReloadType.OTHER};
}
