package pro.reactions.modules;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleDescription {
	String[] authors() default {"Unknown author"};
	String description() default "No description";
}