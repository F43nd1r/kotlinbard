package io.github.enjoydambience.kotlinbard;

import com.squareup.kotlinpoet.PropertySpec;
import com.squareup.kotlinpoet.TypeName;

class Access {
    static TypeName getTypeOf(PropertySpec.Builder builder) {
        //noinspection KotlinInternalInJava
        return builder.getType$kotlinpoet();
    }
}
